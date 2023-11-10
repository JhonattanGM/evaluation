package cl.evaluation.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import cl.evaluation.exercise.utils.ValidationUtil;

@SpringBootTest
public class ValidationUtilTest {

  @Test
  void validationMailSuccess() {
    final Boolean result = ValidationUtil.validateEmail("aaa@aaa.cl");
    Assertions.assertTrue(result);
  }

  @Test
  void validationMailFailure() {
    final Boolean result = ValidationUtil.validateEmail("aaa@@aaa.cl");
    Assertions.assertFalse(result);
  }

  @Test
  void validatePasswordSuccess() {
    final Boolean result = ValidationUtil.validatePassword("Aaa1a5");
    Assertions.assertTrue(result);
  }

  @Test
  void validatePasswordFailure() {
    final Boolean result = ValidationUtil.validatePassword("aaaa5ss");
    Assertions.assertFalse(result);
  }
}
