package cl.evaluation.exercise.config;

import java.net.BindException;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.TypeMismatchException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class ErrorHandler {

  @Value("${msg.exception.page-not-found}")
  private String PAGE_NOT_FOUND;

  @Value("${msg.exception.method-not-supported}")
  private String METHOD_NOT_SUPPORTED;

  @Value("${msg.exception.media-type-not-supported}")
  private String MEDIA_TYPE_NOT_SUPPORTED;

  @Value("${msg.exception.media-type-not-acceptable}")
  private String MEDIA_TYPE_NOT_ACCEPTABLE;

  @Value("${msg.exception.missing-path-variable}")
  private String MISSING_PATH_VARIABLE;

  @Value("${msg.exception.missing-servlet-request-parameter}")
  private String MISSING_SERVLET_REQUEST_PARAMETER;

  @Value("${msg.exception.missing-servlet-request-part}")
  private String MISSING_SERVLET_REQUEST_PART;

  @Value("${msg.exception.binding}")
  private String BINDING;

  @Value("${msg.exception.method-argument-not-valid}")
  private String METHOD_ARGUMENT_NOT_VALID;

  @Value("${msg.exception.async-request-timeout}")
  private String ASYNC_REQUEST_TIMEOUT;

  @Value("${msg.exception.error-response}")
  private String ERROR_RESPONSE;

  @Value("${msg.exception.conversion-not-supported}")
  private String CONVERSION_NOT_SUPPORTED;

  @Value("${msg.exception.type-mismatch}")
  private String TYPE_MISMATCH;

  @Value("${msg.exception.message-not-readable}")
  private String MESSAGE_NOT_READABLE;

  @Value("${msg.exception.message-not-writable}")
  private String MESSAGE_NOT_WRITABLE;

  @Value("${msg.exception.internal-server-error-base}")
  private String INTERNAL_SERVER;

  @Value("${msg.response.property}")
  private String RESPONSE_PROPERTY;


  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
    return newResponse(PAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Object> handleMethodNotAllowedException(
      HttpRequestMethodNotSupportedException ex) {
    return newResponse(METHOD_NOT_SUPPORTED, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<Object> handleUnsupportedMediaTypeException(
      HttpMediaTypeNotSupportedException ex) {
    return newResponse(MEDIA_TYPE_NOT_SUPPORTED, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<Object> handleMediaTypeNotAcceptableException(
      HttpMediaTypeNotAcceptableException ex) {
    return newResponse(MEDIA_TYPE_NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<Object> handleMissingPathVariableException(
      MissingPathVariableException ex) {
    return newResponse(MISSING_PATH_VARIABLE, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Object> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {
    return newResponse(MISSING_SERVLET_REQUEST_PARAMETER, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  public ResponseEntity<Object> handleMissingServletRequestPartException(
      MissingServletRequestPartException ex) {
    return newResponse(MISSING_SERVLET_REQUEST_PART, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ServletRequestBindingException.class)
  public ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException ex) {
    return newResponse(BINDING, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    return newResponse(METHOD_ARGUMENT_NOT_VALID, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AsyncRequestTimeoutException.class)
  public ResponseEntity<Object> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException ex) {
    return newResponse(ASYNC_REQUEST_TIMEOUT,
        HttpStatus.GATEWAY_TIMEOUT);
  }

  @ExceptionHandler(ErrorResponseException.class)
  public ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex) {
    return newResponse(ERROR_RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ConversionNotSupportedException.class)
  public ResponseEntity<Object> handleConversionNotSupportedException(
      ConversionNotSupportedException ex) {
    return newResponse(CONVERSION_NOT_SUPPORTED, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(TypeMismatchException.class)
  public ResponseEntity<Object> handleTypeMismatchException(TypeMismatchException ex) {
    return newResponse(TYPE_MISMATCH, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    return newResponse(MESSAGE_NOT_READABLE, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotWritableException.class)
  public ResponseEntity<Object> handleHttpMessageNotWritableException(
      HttpMessageNotWritableException ex) {
    return newResponse(MESSAGE_NOT_WRITABLE,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<Object> handleBindException(BindException ex) {
    return newResponse(BINDING, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception ex) {
    return newResponse(INTERNAL_SERVER + " " + ex.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }


  private ResponseEntity<Object> newResponse(String message, HttpStatus httpStatus) {
    Map<String, Object> httpResponse = new HashMap<>();
    httpResponse.put(RESPONSE_PROPERTY, message);
    return new ResponseEntity<>(httpResponse, httpStatus);
  }

}

