package academy.digitallab.store.product.controller;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.http.request.ProductRequest;
import academy.digitallab.store.product.http.response.ProductResponse;
import academy.digitallab.store.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping (value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService ;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
      log.error("listar Productos - hilo: " + Thread.currentThread().getName());
      List<ProductResponse> products = new ArrayList<>();
        if (null ==  categoryId){
             products = productService.listAllProduct();
            if (products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            products = productService.findProductByCategory(
                    Category.builder().id(categoryId).build());
            if (products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

      HttpHeaders headers = new HttpHeaders();
      headers.add("Custom-header", "foo");
      // el responseEntity retorna el Header, Body y un HttpStatus
      return new ResponseEntity<List<ProductResponse>>(products, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
      ProductResponse product =  productService.getProduct(id);
      if (null==product){
          return ResponseEntity.notFound().build();
      }
      return new ResponseEntity<ProductResponse>(product, HttpStatus.OK);
//      return ResponseEntity.ok(product);
    }

  /**
   * PRINCIPIO DE IDEMPOTENCIA: Post no tiene idempotencia, si lo tiene get put, delete.
   * Para eso implementaremos idempotencia en el POST, cuando creas un recurso solo deberia registrarse una osla vez.
   * Si hay varios clientes q llamen a ese recurrso, deberia retornar el recurso creado inicialmente por algun cliente,
   * de esta manera ya no tendremos 2 recursos iguales en nuestra BD.
   * @param product
   * @param result
   * @return
   */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest product,
                                                         BindingResult result) {
      if (result.hasErrors()){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
      }
      ProductResponse productCreate =  productService.createProduct(product);
      return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest product){
        product.setId(id);
        ProductResponse productDB =  productService.updateProduct(product);
        if (productDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable("id") Long id){
        ProductResponse productDelete = productService.deleteProduct(id);
        if (productDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @PutMapping (value = "/{id}/stock")
    public ResponseEntity<ProductResponse> updateStockProduct(
            @PathVariable  Long id,
            @RequestParam(name = "quantity", required = true) Double quantity){

        ProductResponse  product = productService.updateStock(id, quantity);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }


}
