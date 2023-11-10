package cl.evaluation.exercise.controllers.responses;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Builder
public class CreateUserResp {

  private Long id;
  private Date created;
  private Date modified;
  private Date last_login;
  private String token;
  private Boolean isactive;

}
