package cl.evaluation.exercise.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cl.evaluation.exercise.controllers.request.CreateUserDTO;
import cl.evaluation.exercise.models.ERole;
import cl.evaluation.exercise.models.PhoneEntity;
import cl.evaluation.exercise.models.RoleEntity;
import cl.evaluation.exercise.models.UserEntity;
import cl.evaluation.exercise.repositories.PhoneRepository;
import cl.evaluation.exercise.repositories.UserRepository;
import cl.evaluation.exercise.security.jwt.JwtUtils;

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
  public UserEntity createUser(CreateUserDTO createUserDTO) {

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

    return userEntity;


  }



}
