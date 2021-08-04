package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
