package academy.digitallab.store.product.util;

import academy.digitallab.store.product.controller.ErrorMessage;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.http.request.ProductRequest;
import academy.digitallab.store.product.http.response.CategoryResponse;
import academy.digitallab.store.product.http.response.ProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Omar Calderon Evangelista
 * User: Omar.Calderon
 * Date: 13/08/2021
 * Time: 4:07 p. m.
 * Change Text
 */
public  class Utils {

  public static List<ProductResponse> buildListProduct(List<Product> request) {
    List<ProductResponse> listaResponse = new ArrayList<>();
    request.forEach(x -> {
      ProductResponse response = new ProductResponse();
      BeanUtils.copyProperties(x, response);
      listaResponse.add(response);
    });
    return listaResponse;
  }

  /**
   * Convierte el Object Product de BD a un Object ProductReponse para el usuario final.
   * @param request
   * @return
   */
  public static Product buildProductRequest(ProductRequest request) {
    Product product = new Product();
    BeanUtils.copyProperties(request, product);
    return product;
  }

  /**
   * Convierte el Object Product de BD a un Object ProductReponse para el usuario final.
   * @param request
   * @return
   */
  public static ProductResponse buildProductResponse(Product request) {
    ProductResponse response = new ProductResponse();
    BeanUtils.copyProperties(request, response);
    response.setCategory(CategoryResponse.builder()
            .id(request.getCategory().getId())
            .name(request.getCategory().getName())
            .build());
    return response;
  }

  public static String formatMessage(BindingResult result){
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
