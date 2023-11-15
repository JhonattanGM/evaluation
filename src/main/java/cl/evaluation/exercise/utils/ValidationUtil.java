package cl.evaluation.exercise.utils;

import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {


  private static String regexEmail;
  private static String regexPassword;

  @Value("${regex.validations.email}")
  public void setRegexEmail(String regex) {
    regexEmail = regex;
  }

  @Value("${regex.validations.password}")
  public void setRegexPassword(String regex) {
    regexPassword = regex;
  }


  public static Boolean validateEmail(String email) {

    Pattern pattern = Pattern.compile(regexEmail);
    return email != null && pattern.matcher(email).matches();
  }

  public static Boolean validatePassword(String password) {

    Pattern pattern = Pattern.compile(regexPassword);
    return password != null && pattern.matcher(password).matches();
  }
}
