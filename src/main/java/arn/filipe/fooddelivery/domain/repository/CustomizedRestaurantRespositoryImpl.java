package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CustomizedRestaurantRespositoryImpl {

    List<Restaurant> customizedFind(String name,
                          BigDecimal freightRateInitial,
                          BigDecimal freightRateFinal);

    List<Restaurant> withFreeShipping(String name);
}
