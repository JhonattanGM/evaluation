package cl.evaluation.exercise.services;

import cl.evaluation.exercise.controllers.request.CreateUserDTO;
import cl.evaluation.exercise.models.UserEntity;

public interface UserService {

  public UserEntity createUser(CreateUserDTO createUserDTO);
}
