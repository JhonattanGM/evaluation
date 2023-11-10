package cl.evaluation.exercise.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cl.evaluation.exercise.controllers.request.CreateUserDTO;
import cl.evaluation.exercise.controllers.responses.CreateUserResp;
import cl.evaluation.exercise.models.ERole;
import cl.evaluation.exercise.models.PhoneEntity;
import cl.evaluation.exercise.models.RoleEntity;
import cl.evaluation.exercise.models.UserEntity;
import cl.evaluation.exercise.repositories.PhoneRepository;
import cl.evaluation.exercise.repositories.UserRepository;
import cl.evaluation.exercise.security.jwt.JwtUtils;
import cl.evaluation.exercise.services.dto.ValidationCreateUserDTO;
import cl.evaluation.exercise.utils.ValidationUtil;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PhoneRepository phoneRepository;


  @Override
  public ResponseEntity<?> createUser(CreateUserDTO createUserDTO) {

    ValidationCreateUserDTO validation = validations(createUserDTO);

    if (validation.getValidation()) {

      String token = jwtUtils.generateAccesToken(createUserDTO.getEmail());
      UserEntity userEntity = UserEntity.builder()
          .name(createUserDTO.getName())
          .email(createUserDTO.getEmail())
          .password(passwordEncoder.encode(createUserDTO.getPassword()))
          .token(token)
          .roles(Set.of(RoleEntity.builder()
              .name(ERole.valueOf(ERole.ADMIN.name()))
              .build()))
          .build();
      userRepository.save(userEntity);

      if (createUserDTO.getPhones() != null && createUserDTO.getPhones().size() > 0) {
        List<PhoneEntity> phones = createUserDTO.getPhones().stream()
            .map(el -> PhoneEntity.builder()
                .userEntity(userEntity)
                .citycode(el.getCitycode())
                .numberPhone(el.getNumberPhone())
                .contrycode(el.getContrycode())
                .build())
            .collect(Collectors.toList());
        phoneRepository.saveAll(phones);
      }


      CreateUserResp user = CreateUserResp.builder()
          .id(userEntity.getId())
          .created(userEntity.getCreated())
          .modified(userEntity.getModified())
          .last_login(userEntity.getLast_login())
          .token(userEntity.getToken())
          .isactive(userEntity.getActive())
          .build();



      return ResponseEntity.status(HttpStatus.OK).body(user);

    } else {
      return validation.getResponse();
    }
  }


  private ValidationCreateUserDTO validations(CreateUserDTO createUserDTO) {

    Boolean valid = false;
    Map<String, Object> httpResponse = new HashMap<>();

    if (!ValidationUtil.validateEmail(createUserDTO.getEmail())) {
      httpResponse.put("mensaje", "Correo inválido");
    } else if (!ValidationUtil.validatePassword(createUserDTO.getPassword())) {
      httpResponse.put("mensaje",
          "Contraseña inválida, debe contener: una mayuscula, letras minúsculas y dos numeros");
    } else {
      Optional<UserEntity> userEmail = userRepository.findByEmail(createUserDTO.getEmail());
      if (!userEmail.isEmpty()) {
        httpResponse.put("mensaje", "El correo ya registrado");
      } else {
        valid = true;
      }
    }

    return ValidationCreateUserDTO.builder()
        .validation(valid)
        .response(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(httpResponse))
        .build();
  }



}
