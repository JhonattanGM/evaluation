package cl.evaluation.exercise.security.exceptionhandlings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    Map<String, Object> extra = new HashMap<>();
    Integer status = response.getStatus();
    String msg = HttpStatus.valueOf(status).getReasonPhrase();
    if (status == 200) {
      status = 403;
      msg = authException.getMessage();
    }
    extra.put("mensaje", msg);

    response.getWriter().write(new ObjectMapper().writeValueAsString(extra));
    response.setStatus(status);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().flush();

  }


}
