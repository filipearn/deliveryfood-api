package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.model.ProductModel;
import arn.filipe.fooddelivery.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ProductModel toModel(Product product){
        return modelMapper.map(product, ProductModel.class);
    }

    public List<ProductModel> toCollectionModel(Collection<Product> products){
        return products.stream()
                .map(product -> toModel(product))
                .collect(Collectors.toList());
    }
}
