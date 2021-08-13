package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.EntityNotFoundException;
import arn.filipe.fooddelivery.domain.exception.RestaurantNotFoundException;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> listAll(){
        return productService.listAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id){
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product product){
        try{
            return productService.save(product);
        } catch (RestaurantNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product){
        try{
            return productService.update(id, product);
        } catch (RestaurantNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        productService.delete(id);
    }
}
