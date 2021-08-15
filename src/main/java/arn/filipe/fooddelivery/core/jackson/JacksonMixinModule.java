package arn.filipe.fooddelivery.core.jackson;

import arn.filipe.fooddelivery.api.model.RestaurantMixin;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule (){
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
    }
}
