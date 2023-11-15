package cl.evaluation.exercise.config;

import java.net.BindException;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.TypeMismatchException;
import org.springframework.beans.ConversionNotSupportedException;
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

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
    return newResponse("Página no encontrada", HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Object> handleMethodNotAllowedException(
      HttpRequestMethodNotSupportedException ex) {
    return newResponse("Método HTTP no permitido", HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<Object> handleUnsupportedMediaTypeException(
      HttpMediaTypeNotSupportedException ex) {
    return newResponse("Tipo de medio no soportado", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<Object> handleMediaTypeNotAcceptableException(
      HttpMediaTypeNotAcceptableException ex) {
    return newResponse("Tipo de medio no aceptable", HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<Object> handleMissingPathVariableException(
      MissingPathVariableException ex) {
    return newResponse("Variable de ruta faltante", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Object> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {
    return newResponse("Parámetro de solicitud faltante", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  public ResponseEntity<Object> handleMissingServletRequestPartException(
      MissingServletRequestPartException ex) {
    return newResponse("Parte de la solicitud faltante", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ServletRequestBindingException.class)
  public ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException ex) {
    return newResponse("Error al vincular los datos de la solicitud", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    return newResponse("Argumento del método no válido", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AsyncRequestTimeoutException.class)
  public ResponseEntity<Object> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException ex) {
    return newResponse("Tiempo de espera de la solicitud asíncrona agotado",
        HttpStatus.GATEWAY_TIMEOUT);
  }

  @ExceptionHandler(ErrorResponseException.class)
  public ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex) {
    return newResponse("Error al procesar la respuesta", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ConversionNotSupportedException.class)
  public ResponseEntity<Object> handleConversionNotSupportedException(
      ConversionNotSupportedException ex) {
    return newResponse("No se puede convertir el objeto", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(TypeMismatchException.class)
  public ResponseEntity<Object> handleTypeMismatchException(TypeMismatchException ex) {
    return newResponse("Tipo de datos no coincidente", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    return newResponse("No se puede leer el mensaje de la solicitud", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotWritableException.class)
  public ResponseEntity<Object> handleHttpMessageNotWritableException(
      HttpMessageNotWritableException ex) {
    return newResponse("No se puede escribir el mensaje de la respuesta",
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<Object> handleBindException(BindException ex) {
    return newResponse("Error al vincular los datos de la solicitud", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception ex) {
    return newResponse("Ha ocurrido un error: " + ex.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }


  private ResponseEntity<Object> newResponse(String message, HttpStatus httpStatus) {
    Map<String, Object> httpResponse = new HashMap<>();
    httpResponse.put("mensaje", message);
    return new ResponseEntity<>(httpResponse, httpStatus);
  }

}

