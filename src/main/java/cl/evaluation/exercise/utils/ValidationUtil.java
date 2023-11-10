package cl.evaluation.exercise.utils;

import java.util.regex.Pattern;

public class ValidationUtil {

  private static final String REGEX_EMAIL = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
  private static final String REGEX_EMAIL_LETTERS = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
  private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d)[A-Za-z\\d]*$";

  public static Boolean validateEmail(String email) {

    Pattern pattern = Pattern.compile(REGEX_EMAIL);
    return email != null && pattern.matcher(email).matches();
  }

  public static Boolean validatePassword(String password) {

    Pattern pattern = Pattern.compile(REGEX_PASSWORD);
    return password != null && pattern.matcher(password).matches();
  }
}
