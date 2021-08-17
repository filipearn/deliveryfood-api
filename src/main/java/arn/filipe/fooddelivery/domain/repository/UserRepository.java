package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CustomizedJpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
