package cl.evaluation.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import cl.evaluation.exercise.controllers.request.CreateUserDTO;
import cl.evaluation.exercise.repositories.UserRepository;
import cl.evaluation.exercise.services.UserService;

@SpringBootTest
public class UserServiceTest {


  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

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
    ResponseEntity<?> response = userService.createUser(userCreate);

    Assertions.assertEquals(response.getBody().toString(),
        "{mensaje=Contraseña inválida, debe contener: una mayuscula, letras minúsculas y dos numeros}");
    Assertions.assertEquals(response.getStatusCode().value(), 422);
  }

  @Test
  void createUserEmailFail() {

    CreateUserDTO userCreate = CreateUserDTO.builder()
        .email("aaa@@ada.cl")
        .password("A2aaa1")
        .build();
    ResponseEntity<?> response = userService.createUser(userCreate);

    Assertions.assertEquals(response.getBody().toString(),
        "{mensaje=Correo inválido}");
    Assertions.assertEquals(response.getStatusCode().value(), 422);
  }

  @Test
  void createUserEmailRegisteredFail() {

    CreateUserDTO userCreate = CreateUserDTO.builder()
        .email("aaa@ada.cl")
        .password("A2aaa1")
        .build();
    userService.createUser(userCreate);
    ResponseEntity<?> response = userService.createUser(userCreate);
    Assertions.assertEquals(response.getBody().toString(), "{mensaje=El correo ya registrado}");
    Assertions.assertEquals(response.getStatusCode().value(), 422);

  }

  @Test
  void createUserSuccess() {

    CreateUserDTO userCreate = CreateUserDTO.builder()
        .email("aaa@ada.cl")
        .password("A2aaa1")
        .build();
    ResponseEntity<?> response = userService.createUser(userCreate);
    Assertions.assertEquals(response.getStatusCode().value(), 200);

  }
}
