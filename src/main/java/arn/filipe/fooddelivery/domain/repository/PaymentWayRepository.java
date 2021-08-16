package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.PaymentWay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentWayRepository extends JpaRepository<PaymentWay, Long> {
}
