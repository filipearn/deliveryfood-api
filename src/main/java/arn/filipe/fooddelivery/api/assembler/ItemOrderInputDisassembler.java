package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.model.input.ItemOrderInput;
import arn.filipe.fooddelivery.domain.model.ItemOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemOrderInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public ItemOrder toDomainObject(ItemOrderInput itemOrderInput){
        return modelMapper.map(itemOrderInput, ItemOrder.class);
    }

    public void copyToDomainObject(ItemOrderInput itemOrderInput, ItemOrder itemOrder){
        modelMapper.map(itemOrderInput, itemOrder);
    }
}
