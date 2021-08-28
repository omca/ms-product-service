package academy.digitallab.store.product.exception;

import academy.digitallab.store.product.http.response.JsonResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorDetails extends JsonResponse{

  public ErrorDetails(String status, String mensaje) {
    this.setStatus(status);
    this.setMessage(mensaje);
  }

}
