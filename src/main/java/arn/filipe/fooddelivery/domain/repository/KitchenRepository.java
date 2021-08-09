package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

    List<Kitchen> findByName(String name);

    List<Kitchen> findByNameContaining(String name);
}
