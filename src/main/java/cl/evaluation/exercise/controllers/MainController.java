package cl.evaluation.exercise.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cl.evaluation.exercise.controllers.request.CreateUserDTO;
import cl.evaluation.exercise.utils.UserUtil;
import jakarta.validation.Valid;


@RestController()
public class MainController {

  @Value("${msg.response.property}")
  private String RESPONSE_PROPERTY;


  @Value("${msg.response.endpoint-secure}")
  private String RESPONSE_ENDPOINT_SECURE;

  @Autowired
  private UserUtil userUtil;

  @GetMapping("/secure")
  public ResponseEntity<Map<String, Object>> secured() {
    Map<String, Object> httpResponse = new HashMap<>();
    httpResponse.put(RESPONSE_PROPERTY, RESPONSE_ENDPOINT_SECURE);
    return ResponseEntity.status(HttpStatus.OK).body(httpResponse);
  }

  @PostMapping("/createUser")
  public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
    return userUtil.createUser(createUserDTO);
  }

}
