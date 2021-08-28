package academy.digitallab.store.product.http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {

  private Long id;

  private String name;

}
