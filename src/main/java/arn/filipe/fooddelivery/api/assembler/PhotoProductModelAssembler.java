package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.model.PhotoProductModel;
import arn.filipe.fooddelivery.domain.model.PhotoProduct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PhotoProductModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PhotoProductModel toModel(PhotoProduct photoProduct){
        return modelMapper.map(photoProduct, PhotoProductModel.class);
    }

    public List<PhotoProductModel> toCollectionModel(Collection<PhotoProduct> photoProducts){
        return photoProducts.stream()
                .map(photoProduct -> toModel(photoProduct))
                .collect(Collectors.toList());
    }
}
