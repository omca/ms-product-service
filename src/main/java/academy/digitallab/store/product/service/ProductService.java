package academy.digitallab.store.product.service;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.http.request.ProductRequest;
import academy.digitallab.store.product.http.response.ProductResponse;

import java.util.List;

public interface ProductService {

    /**
     *
     * @return
     */
    public List<ProductResponse> listAllProduct();

    /**
     *
     * @return
     */
    public ProductResponse getProduct(Long id);

    /**
     *
     * @return
     */
    public ProductResponse createProduct(ProductRequest product);

    /**
     *
     * @return
     */
    public ProductResponse updateProduct(ProductRequest product);

    /**
     *
     * @return
     */
    public  ProductResponse deleteProduct(Long id);

    /**
     *
     * @return
     */
    public List<ProductResponse> findProductByCategory(Category category);

    /**
     *
     * @return
     */
    public ProductResponse updateStock(Long id, Double quantity);
}
