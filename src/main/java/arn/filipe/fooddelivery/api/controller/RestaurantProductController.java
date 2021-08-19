package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.ProductInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.ProductModelAssembler;
import arn.filipe.fooddelivery.api.model.ProductModel;
import arn.filipe.fooddelivery.api.model.input.ProductInput;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.service.ProductService;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants/{restaurantId}/products")
public class RestaurantProductController {

    @Autowired
    private ProductModelAssembler productModelAssembler;

    @Autowired
    private ProductInputDisassembler productInputDisassembler;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductModel> listAll(@PathVariable Long restaurantId){
        Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(restaurantId);

        return productModelAssembler.toCollectionModel(restaurant.getProducts());
    }

    @GetMapping("/{productId}")
    public ProductModel findById(@PathVariable Long restaurantId, @PathVariable Long productId){
        Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(restaurantId);
        Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

        if(restaurant.containsProduct(product)){
            return productModelAssembler.toModel(product);
        }
        else{
            throw new BusinessException(
                    String.format("Product with id '%s' not found for restaurant with id '%s'", productId, restaurantId));
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel save(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput){
        Restaurant restaurant = restaurantService.verifyIfExistsOrThrow(restaurantId);

        Product product = productInputDisassembler.toDomainObject(productInput);
        product.setRestaurant(restaurant);

        product = productService.save(product);

        return productModelAssembler.toModel(product);
    }

    @PutMapping("/{productId}")
    public ProductModel update(@PathVariable Long restaurantId,
                               @PathVariable Long productId,
                               @RequestBody @Valid ProductInput productInput){

        Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

        productInputDisassembler.copyToDomainObject(productInput, product);

        product = productService.save(product);

        return productModelAssembler.toModel(product);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId, @PathVariable Long productId){

        Product product = productService.verifyIfExistsOrThrow(restaurantId, productId);

        restaurantService.disassociateProduct(restaurantId, productId);
        productService.delete(productId);
    }
}
