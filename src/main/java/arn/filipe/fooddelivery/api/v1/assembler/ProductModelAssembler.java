package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.api.v1.controller.RestaurantProductController;
import arn.filipe.fooddelivery.api.v1.model.ProductModel;
import arn.filipe.fooddelivery.core.security.Security;
import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private Security security;

    public ProductModelAssembler(){
        super(RestaurantProductController.class, ProductModel.class);
    }

    public ProductModel toModel(Product product){
        ProductModel productModel = createModelWithId(product.getId(), product, product.getRestaurant().getId());

        modelMapper.map(product, productModel);

        if(security.canFindRestaurants()){
            productModel.add(buildLinks.linkToRestaurantProducts(product.getRestaurant().getId(), "products"));

            try{
                productModel.add(buildLinks.linkToRestaurantProductPhoto(
                        product.getRestaurant().getId(), productModel.getId(), "photo"));

            } catch (Exception e){
                throw new BusinessException("Photo error!");
            }

        }

        return productModel;
    }


}
