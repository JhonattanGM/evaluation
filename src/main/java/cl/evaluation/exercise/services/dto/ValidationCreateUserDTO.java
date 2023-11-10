package cl.evaluation.exercise.services.dto;

import org.springframework.http.ResponseEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidationCreateUserDTO {

  private Boolean validation;
  private ResponseEntity<?> response;
}
