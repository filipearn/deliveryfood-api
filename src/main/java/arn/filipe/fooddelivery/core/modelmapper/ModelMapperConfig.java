package arn.filipe.fooddelivery.core.modelmapper;

import arn.filipe.fooddelivery.api.model.AddressModel;
import arn.filipe.fooddelivery.api.model.input.ItemOrderInput;
import arn.filipe.fooddelivery.domain.model.Address;
import arn.filipe.fooddelivery.domain.model.ItemOrder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean

    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemOrderInput.class, ItemOrder.class)
                .addMappings(mapper -> mapper.skip(ItemOrder::setId));



        var addressToAddressModelTypeMap = modelMapper.createTypeMap(
                Address.class, AddressModel.class);

        addressToAddressModelTypeMap.<String>addMapping(
                addressSrc -> addressSrc.getCity().getState().getName(),
                (addressModelDest, value) -> addressModelDest.getCity().setState(value));

        return modelMapper;
    }
}
