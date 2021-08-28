package academy.digitallab.store.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity // es un collection
@Table(name = "tbl_products")
@Data //getter and setter, tostring(), hash
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no debe ser vacío")
    private String name;

    private String description;

    private Double stock;

    private Double price;

    private String status;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;


    // muchos a uno, una categoria tiene muchos productos
    // EAGER, carga de manera proactiva todos los valores de la categoria
    // LAZY, carga en base a las solicitudes que se hagan
    @ManyToOne(fetch = FetchType.LAZY)
    // "category_id" es el nombre del campo en la BdE
    @JoinColumn(name = "category_id")

    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Category category;

}
