package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.ProductRepository;
import arn.filipe.fooddelivery.domain.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public static final String PRODUCT_IN_USE = "Product with id %d can't be removed. Resource in use.";
    public static final String PRODUCT_NOT_FOUND = "Product with id %d not found.";
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantService restaurantService;

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return verifyIfExistsOrThrow(id);
    }

    public Product save(Product product) {
        Long restaurantId = product.getRestaurant().getId();

        Restaurant restaurant = restaurantService.findById(restaurantId);

        product.setRestaurant(restaurant);

        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        Long restaurantId = product.getRestaurant().getId();

        Restaurant restaurant = restaurantService.findById(restaurantId);

        Product productToUpdate = verifyIfExistsOrThrow(id);

        BeanUtils.copyProperties(product, productToUpdate, "id");

        productToUpdate.setRestaurant(restaurant);

        return productRepository.save(productToUpdate);
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    String.format(PRODUCT_NOT_FOUND, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(PRODUCT_IN_USE, id));
        }
    }

    private Product verifyIfExistsOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Product with id %d not found.", id)));
    }
}
