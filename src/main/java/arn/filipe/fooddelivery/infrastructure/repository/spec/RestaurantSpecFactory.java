package arn.filipe.fooddelivery.infrastructure.repository.spec;

import arn.filipe.fooddelivery.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecFactory {

    public static Specification<Restaurant> freeShipping(){
        return (root, query, builder) ->
                builder.equal(root.get("freightRate"), BigDecimal.ZERO);
    }

    public static Specification<Restaurant> withSimilarName(String name){
        return (root, query, builder) ->
                builder.like(root.get("name"), "%" + name + "%");
    }
}
