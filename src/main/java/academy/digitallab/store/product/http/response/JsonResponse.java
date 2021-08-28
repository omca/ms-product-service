package academy.digitallab.store.product.http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class JsonResponse {

  /**
   * Codigo de respuesta funcional para la peticion
   */
  private String status;

  /**
   * Mensaje de respuesta funcional para la peticion
   */
  private String message;

  /**
   * Contiene el cuerpo del response
   */
  private Object result;

  private Date time;

 
}