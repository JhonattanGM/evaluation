package cl.evaluation.exercise.services;

import org.springframework.http.ResponseEntity;
import cl.evaluation.exercise.controllers.request.CreateUserDTO;

public interface UserService {

  public ResponseEntity<?> createUser(CreateUserDTO createUserDT);
}
