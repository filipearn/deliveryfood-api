package arn.filipe.fooddelivery.api.assembler;

import arn.filipe.fooddelivery.api.model.input.CityInput;
import arn.filipe.fooddelivery.domain.model.City;
import arn.filipe.fooddelivery.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public City toDomainObject(CityInput cityInput){
        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomainObject(CityInput cityInput, City city){
        // To avoid org.hibernate.HibernateException: identifier of an instance of
        // domain.model.state was altered from 1 to 2
        city.setState(new State());

        modelMapper.map(cityInput, city);
    }

}
