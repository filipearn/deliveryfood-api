package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.ProductNotFoundException;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    public static final String PRODUCT_IN_USE = "Product with id %d can't be removed. Resource in use.";
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Transactional
    public Product save(Product product) {
       return productRepository.save(product);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
            productRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(PRODUCT_IN_USE, id));
        }
    }

    public List<Product> findAllByRestaurant(Restaurant restaurant){
        return productRepository.findAllByRestaurant(restaurant);
    }

    public List<Product> findActiveByRestaurant(Restaurant restaurant){
        return productRepository.findActiveByRestaurant(restaurant);
    }

    public Product verifyIfExistsOrThrow(Long restaurantId, Long productId) {

        return productRepository.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format("Product with id %d not found.", productId)));
    }
}
