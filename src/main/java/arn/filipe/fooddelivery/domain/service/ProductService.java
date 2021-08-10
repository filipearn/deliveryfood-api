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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Product with id %d not found.", id)));
    }


    public Product save(Product product) {
        Long restaurantId = product.getRestaurant().getId();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Restaurant with id %d not found.", restaurantId)));

        product.setRestaurant(restaurant);

        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        Long restaurantId = product.getRestaurant().getId();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Restaurant with id %d not found.", restaurantId)));

        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Product with id %d not found.", id)));

        BeanUtils.copyProperties(product, productToUpdate, "id");

        productToUpdate.setRestaurant(restaurant);

        return productRepository.save(productToUpdate);
    }

    public void delete(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Product with id %d not found.", id)));

        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Product with id %d can't be removed. Resource in use.", id));
        }
    }
}
