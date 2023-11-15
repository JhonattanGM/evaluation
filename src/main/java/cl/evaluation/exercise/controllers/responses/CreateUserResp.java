package cl.evaluation.exercise.controllers.responses;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Builder
public class CreateUserResp {

  private Long id;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date created;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date modified;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date last_login;
  private String token;
  private Boolean isactive;

}
