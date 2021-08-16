package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.exception.ProductNotFoundException;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.ProductRepository;
import arn.filipe.fooddelivery.domain.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return verifyIfExistsOrThrow(id);
    }

    @Transactional
    public Product save(Product product) {
        Long restaurantId = product.getRestaurant().getId();

        Restaurant restaurant = restaurantService.findById(restaurantId);

        product.setRestaurant(restaurant);

        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, Product product) {
        Long restaurantId = product.getRestaurant().getId();

        Restaurant restaurant = restaurantService.findById(restaurantId);

        Product productToUpdate = verifyIfExistsOrThrow(id);

        BeanUtils.copyProperties(product, productToUpdate, "id");

        productToUpdate.setRestaurant(restaurant);

        return productRepository.save(productToUpdate);
    }

    @Transactional
    public void delete(Long id) {
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

    public Product verifyIfExistsOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format("Product with id %d not found.", id)));
    }
}
