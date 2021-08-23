package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.PhotoProduct;
import arn.filipe.fooddelivery.domain.model.Product;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CustomizedJpaRepository<Product, Long>, ProductRepositoryQueries {

    @Query("from Product where restaurant.id = :restaurant and id = :product")
    Optional<Product> findById(@Param("restaurant") Long restaurantId,
                               @Param("product") Long productId);

    List<Product> findByRestaurant(Restaurant restaurant);

    @Query("select photo from PhotoProduct photo join photo.product p " +
            "where p.restaurant.id= :restaurantId and photo.product.id= :productId")
    Optional<PhotoProduct> findPhotoById(Long restaurantId, Long productId);
}
