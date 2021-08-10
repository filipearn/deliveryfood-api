package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository
        extends CustomizedJpaRepository<Restaurant, Long>, CustomizedRestaurantRespositoryImpl,
        JpaSpecificationExecutor<Restaurant> {

    //Get restaurants using orm query on orm.xml (META_INF) file
    List<Restaurant> queryByNameAndKitchen(String name, @Param("id") Long kitchen);

    @Query("from Restaurant r join fetch r.kitchen left join fetch r.paymentWay")
    List<Restaurant> findAll();
}
