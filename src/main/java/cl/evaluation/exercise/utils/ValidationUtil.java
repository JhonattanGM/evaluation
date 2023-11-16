package cl.evaluation.exercise.utils;

import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {


  private static String REGEX_EMAIL;
  private static String REGEX_PASSWORD;

  @Value("${regex.validations.email}")
  public void setRegexEmail(String regex) {
    REGEX_EMAIL = regex;
  }

  @Value("${regex.validations.password}")
  public void setRegexPassword(String regex) {
    REGEX_PASSWORD = regex;
  }


  public static Boolean validateEmail(String email) {

    Pattern pattern = Pattern.compile(REGEX_EMAIL);
    return email != null && pattern.matcher(email).matches();
  }

  public static Boolean validatePassword(String password) {

    Pattern pattern = Pattern.compile(REGEX_PASSWORD);
    return password != null && pattern.matcher(password).matches();
  }
}
