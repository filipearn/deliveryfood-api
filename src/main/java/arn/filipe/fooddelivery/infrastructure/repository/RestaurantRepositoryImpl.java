package arn.filipe.fooddelivery.infrastructure.repository;

import static arn.filipe.fooddelivery.infrastructure.repository.spec.RestaurantSpecFactory.*;

import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.repository.CustomizedRestaurantRespositoryImpl;
import arn.filipe.fooddelivery.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements CustomizedRestaurantRespositoryImpl {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> customizedFind(String name,
                                 BigDecimal freighRateInitial,
                                 BigDecimal freighRateFinal){

        var jpql = "from Restaurant where name like :name " +
                "and freighRate between :freighRateInitial and :freighRateFinal";

        return manager.createQuery(jpql, Restaurant.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("freighRateInitial", freighRateInitial)
                .setParameter("freighRateFinal", freighRateFinal)
                .getResultList();
    }

    @Override
    public List<Restaurant> withFreeShipping(String name){
        return restaurantRepository.findAll(freeShipping().and(withSimilarName(name)));
    }
}
