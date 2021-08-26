package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.PaymentWay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface PaymentWayRepository extends CustomizedJpaRepository<PaymentWay, Long> {

    @Query("select max(updateDate) from payment_way")
    OffsetDateTime getLastUpdateDate();

    @Query("select updateDate from payment_way where id= :paymentWayId")
    OffsetDateTime getLastUpdateDateById(Long paymentWayId);
}
