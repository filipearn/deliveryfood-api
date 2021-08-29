package arn.filipe.fooddelivery.api.v2.assembler;

import arn.filipe.fooddelivery.api.v2.model.input.CityInputV2;
import arn.filipe.fooddelivery.domain.model.City;
import arn.filipe.fooddelivery.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public City toDomainObject(CityInputV2 cityInput){
        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomainObject(CityInputV2 cityInput, City city){
        // To avoid org.hibernate.HibernateException: identifier of an instance of
        // domain.model.state was altered from 1 to 2
        city.setState(new State());

        modelMapper.map(cityInput, city);
    }

}
