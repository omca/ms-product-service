package academy.digitallab.store.product;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.repository.ProductRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

/**
 * TESTEO DE UN JPA
 * Se hace de manera directa a la BD, haciendo un Autowired al JPA y se insertará un producto en la BD
 * En esta clase no se ha mockeado nada
 */
@DataJpaTest
public class ProductRepositoryMockTest {

  // estasaccediendo directamente a la BD H2
  @Autowired
  private ProductRepository productRepository;

  @Test
  @Disabled
  public void whenFindByCategory_thenReturnListProduct(){

    // given
    Product product = Product.builder()
            .name("computer")
            .category(Category.builder().id(1L).build())
            .description("")
            .stock(Double.parseDouble("10"))
            .price(Double.parseDouble("1240.99"))
            .status("Created")
            .createAt(new Date())
            .build();
    productRepository.save(product);

    // when
    List<Product> productos = productRepository.findProductByCategory(
            product.getCategory());

    // verify
    Assertions.assertThat(productos.size()).isEqualTo(2);

  }

}
