package arn.filipe.fooddelivery.api.v1.assembler;

import arn.filipe.fooddelivery.api.v1.model.input.KitchenInput;
import arn.filipe.fooddelivery.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KitchenInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Kitchen toDomainObject(KitchenInput KitchenInput){
        return modelMapper.map(KitchenInput, Kitchen.class);
    }

    public void copyToDomainObject(KitchenInput kitchenInput, Kitchen kitchen){
        modelMapper.map(kitchenInput, kitchen);
    }
}
