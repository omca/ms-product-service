package academy.digitallab.store.product.service;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.http.request.ProductRequest;
import academy.digitallab.store.product.http.response.ProductResponse;
import academy.digitallab.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static academy.digitallab.store.product.util.Utils.*;

@Service
// agregando inyeccion de dependencias por constructor, es decir, la clase ProductServiceimplements
// recibirá el objeto del Repository (ProductRepository), y ya no habrá necesidad de instanciarlo en sus clientes.
// esto se modifica para las pruebas unitarias
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> listAllProduct() {
        return buildListProduct(productRepository.findAll());
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        // forma 4
        return product.map(p -> buildProductResponse(p)).orElse(null);

        // forma 3
//        return product.map(new Function<Product, Product>() {
//            @Override
//            public Product apply(Product product) {
//                return product;
//            }
//        }).orElse(null);

        // forma 2
//        if (product.isPresent()){
//            return product.get();
//        } else {
//            return null;
//        }

        // forma 1
//      return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        request.setStatus("CREATED");
        request.setCreateAt(new Date());

        return buildProductResponse(
                productRepository.save(buildProductRequest(request))
        );
    }

    @Override
    public ProductResponse updateProduct(ProductRequest product) {
//        ProductResponse productDB = getProduct(product.getId());
        Optional<Product> productDB = productRepository.findById(product.getId());
        return productDB
                .map(p -> {
                    p.setName(product.getName());
                    p.setDescription(product.getDescription());
                    p.setCategory(product.getCategory());
                    p.setPrice(product.getPrice());
                    p.setStatus("UPDATED");
                    return buildProductResponse(productRepository.save(p));
                })
                .orElse(null);
//        return buildProductRequest(productRepository.save(buildProductRequest(productDB));
    }

    @Override
    public ProductResponse deleteProduct(Long id) {
//        Product productDB = getProduct(id);
//        Optional<Product> productDB = productRepository.findById(id);
        return productRepository.findById(id).map(p -> {
            p.setStatus("DELETED");
            return buildProductResponse(productRepository.save(p));
        }).orElse(null);
    }

    @Override
    public List<ProductResponse> findProductByCategory(Category category) {
        return buildListProduct(productRepository.findProductByCategory(category));
    }

    @Override
    public ProductResponse updateStock(Long id, Double quantity) {

        Optional<Product> productDB = productRepository.findById(id);

        return productDB.map(p -> {
            Double stock =  p.getStock() + quantity;
            p.setStock(stock);
            p.setStatus("UPDATED");
            return buildProductResponse(productRepository.save(p));
        }).orElse(null);

    }



}
