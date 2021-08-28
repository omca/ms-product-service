package academy.digitallab.store.product.http.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private Double stock;

    private Double price;

    private String status;

    private Date createAt;

    private Long categoryId;

    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private CategoryResponse category;

}
