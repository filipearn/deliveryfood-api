package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CustomizedJpaRepository<Team, Long> {
}
