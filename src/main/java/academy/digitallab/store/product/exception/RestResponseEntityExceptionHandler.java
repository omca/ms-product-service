package academy.digitallab.store.product.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  @ResponseBody
  public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails("0001", "json mal formado");
    log.error("ERROR JSON MAL FORMADO: " + ex.getMessage());
    ex.printStackTrace();
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}