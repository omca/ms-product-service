package academy.digitallab.store.product.http.request;

import academy.digitallab.store.product.entity.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Getter
@Setter
public class ProductRequest {

    private Long id;

    @NotNull(message = "El nombre no debe ser nulo")
    @NotEmpty(message = "El nombre no debe ser vacío")
    private String name;

    private String description;

    @Positive(message = "El stock debe ser mayor que cero")
    private Double stock;

    private Double price;

    private String status;

    private Date createAt;

    private Long categoryId;

    @NotNull(message = "La categoria no puede ser vacia")
    private Category category;


}
