package cl.evaluation.exercise.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cl.evaluation.exercise.controllers.request.CreateUserDTO;
import cl.evaluation.exercise.services.UserService;
import jakarta.validation.Valid;


@RestController()
public class MainController {

  @Autowired
  private UserService userService;

  @GetMapping("/secure")
  public ResponseEntity<Map<String, Object>> helloSecured() {
    Map<String, Object> httpResponse = new HashMap<>();
    httpResponse.put("mensaje", "Endpoint seguro");
    return ResponseEntity.status(HttpStatus.OK).body(httpResponse);
  }

  @PostMapping("/createUser")
  public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
    return userService.createUser(createUserDTO);
  }

}
