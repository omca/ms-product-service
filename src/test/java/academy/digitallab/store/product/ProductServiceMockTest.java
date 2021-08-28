package academy.digitallab.store.product;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.http.response.ProductResponse;
import academy.digitallab.store.product.repository.ProductRepository;
import academy.digitallab.store.product.service.ProductService;
import academy.digitallab.store.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * Se trabaj con data mockeada.
 * En method Setup() seinicializa la data de prueba, tb se podria inicializar en cada metodo, sería un given()
 */
@SpringBootTest
public class ProductServiceMockTest {

  // el acceso a datos será mockeado (simulado)
  @Mock
  private ProductRepository productRepository;

  private ProductService productService;

  Product computer;
  // antes de empezar, mockearemos nuestros datos para realizar la prueba unitaria
  @BeforeEach
  public void setup(){
    MockitoAnnotations.initMocks(this);

    // le pasamos por constructor el objeto productRepository (mockeado) a la clase ProductServiceImpl
    productService =  new ProductServiceImpl(productRepository);
    Product computer =  Product.builder()
            .id(1L)
            .name("computer")
            .category(Category.builder().id(1L).build())
            .price(Double.parseDouble("12.5"))
            .stock(Double.parseDouble("5"))
            .build();

    // antes de emmpezar las pruebas, mockeamos
    // 1. cuando busque por un ID, retornará un optional.of(Computer)
    Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(computer));

    // 2.
    Mockito.when(productRepository.save(computer)).thenReturn(computer);
  }

  @Test
  public void whenValidGetID_ThenReturnProduct(){

    // given: mockeamos el repository que el service lo va a usar (se preparan los valores de retorno de las pruebas)

    // when (llamada al metodo que se prueba, es este caso, la llamada al metodo del service)
    ProductResponse found = productService.getProduct(1L);

    // verify: se verifica los resultado del given con el when
    Assertions.assertThat(found.getName()).isEqualTo("computer");
   }

  @Test
  public void whenValidUpdateStock_ThenReturnNewStock(){

    // given

    // when
    ProductResponse newStock = productService.updateStock(1L,Double.parseDouble("8"));

    // verify
    Assertions.assertThat(newStock.getStock()).isEqualTo(13);
  }
}
