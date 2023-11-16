package cl.evaluation.exercise.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import cl.evaluation.exercise.controllers.request.CreateUserDTO;
import cl.evaluation.exercise.controllers.responses.CreateUserResp;
import cl.evaluation.exercise.models.UserEntity;
import cl.evaluation.exercise.services.UserService;
import cl.evaluation.exercise.services.dto.ValidationCreateUserDTO;

@Component
public class UserUtil {

  @Value("${msg.response.property}")
  private String RESPONSE_PROPERTY;

  @Value("${msg.validation.email.invalid}")
  private String EMAIL_INVALID;

  @Value("${msg.validation.email.registered}")
  private String EMAIL_REGISTERED;

  @Value("${msg.validation.password.invalid}")
  private String PASSWORD_INVALID;



  @Autowired
  private UserService userService;


  public ResponseEntity<?> createUser(CreateUserDTO createUserDTO) {
    ValidationCreateUserDTO validation = validations(createUserDTO);

    if (validation.getValidation()) {

      try {
        UserEntity userEntity = userService.createUser(createUserDTO);
        CreateUserResp user = CreateUserResp.builder()
            .id(userEntity.getId())
            .created(userEntity.getCreated())
            .modified(userEntity.getModified())
            .last_login(userEntity.getLast_login())
            .token(userEntity.getToken())
            .isactive(userEntity.getActive())
            .build();
        return ResponseEntity.status(HttpStatus.OK).body(user);
      } catch (DataIntegrityViolationException ex) {
        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put(RESPONSE_PROPERTY, EMAIL_REGISTERED);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(httpResponse);
      }

    } else {
      return validation.getResponse();
    }
  }

  private ValidationCreateUserDTO validations(CreateUserDTO createUserDTO) {

    Boolean valid = false;
    Map<String, Object> httpResponse = new HashMap<>();

    if (!ValidationUtil.validateEmail(createUserDTO.getEmail())) {
      httpResponse.put(RESPONSE_PROPERTY, EMAIL_INVALID);
    } else if (!ValidationUtil.validatePassword(createUserDTO.getPassword())) {
      httpResponse.put(RESPONSE_PROPERTY, PASSWORD_INVALID);
    } else {
      valid = true;
    }

    return ValidationCreateUserDTO.builder()
        .validation(valid)
        .response(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(httpResponse))
        .build();
  }
}
