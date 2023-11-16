package cl.evaluation.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import cl.evaluation.exercise.controllers.request.CreateUserDTO;
import cl.evaluation.exercise.repositories.UserRepository;
import cl.evaluation.exercise.utils.UserUtil;

@SpringBootTest
public class UserUtilTest {

  @Value("${msg.response.property}")
  private String RESPONSE_PROPERTY;

  @Value("${msg.validation.email.invalid}")
  private String EMAIL_INVALID;

  @Value("${msg.validation.email.registered}")
  private String EMAIL_REGISTERED;

  @Value("${msg.validation.password.invalid}")
  private String PASSWORD_INVALID;


  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserUtil userUtil;

  @BeforeEach
  public void setup() {
    userRepository.deleteAll();
  }

  @Test
  void createUserPasswordFail() {

    CreateUserDTO userCreate = CreateUserDTO.builder()
        .email("aaa@ada.cl")
        .password("aaa1")
        .build();
    ResponseEntity<?> response = userUtil.createUser(userCreate);

    Assertions.assertEquals(response.getBody().toString(),
        "{" + RESPONSE_PROPERTY + "=" + PASSWORD_INVALID + "}");
    Assertions.assertEquals(response.getStatusCode().value(), 422);

  }

  @Test
  void createUserEmailFail() {

    CreateUserDTO userCreate = CreateUserDTO.builder()
        .email("aaa@@ada.cl")
        .password("A2aaa1")
        .build();
    ResponseEntity<?> response = userUtil.createUser(userCreate);

    Assertions.assertEquals(response.getBody().toString(),
        "{" + RESPONSE_PROPERTY + "=" + EMAIL_INVALID + "}");
    Assertions.assertEquals(response.getStatusCode().value(), 422);
  }

  @Test
  void createUserEmailRegisteredFail() {

    CreateUserDTO userCreate = CreateUserDTO.builder()
        .email("aaa@ada.cl")
        .password("A2aaa1")
        .build();
    userUtil.createUser(userCreate);
    ResponseEntity<?> response = userUtil.createUser(userCreate);

    Assertions.assertEquals(response.getBody().toString(),
        "{" + RESPONSE_PROPERTY + "=" + EMAIL_REGISTERED + "}");
    Assertions.assertEquals(response.getStatusCode().value(), 422);

  }


  @Test
  void createUserSuccess() {

    CreateUserDTO userCreate = CreateUserDTO.builder()
        .email("aaa@ada.cl")
        .password("A2aaa1")
        .build();
    ResponseEntity<?> response = userUtil.createUser(userCreate);
    Assertions.assertEquals(response.getStatusCode().value(), 200);

  }



}
