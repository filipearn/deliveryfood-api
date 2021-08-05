package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
