package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.ItemOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrderRepository extends CustomizedJpaRepository<ItemOrder, Long> {

}
