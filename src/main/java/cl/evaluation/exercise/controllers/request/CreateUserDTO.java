package cl.evaluation.exercise.controllers.request;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDTO {

  private String name;

  private String email;

  private String password;

  private Set<PhoneDTO> phones;
}
