package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.ProductInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.ProductModelAssembler;
import arn.filipe.fooddelivery.api.model.ProductModel;
import arn.filipe.fooddelivery.api.model.input.ProductInput;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.service.ProductService;
import arn.filipe.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductModelAssembler productModelAssembler;

    @Autowired
    ProductInputDisassembler productInputDisassembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel save(@RequestBody ProductInput productInput){
        Product product = productInputDisassembler.toDomainObject(productInput);

        return productModelAssembler.toModel(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        productService.delete(id);
    }
}
