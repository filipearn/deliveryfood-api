package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends CustomizedJpaRepository<PurchaseOrder, Long> {

    @Query("from PurchaseOrder p join fetch p.client join fetch p.restaurant r join fetch r.kitchen")
    List<PurchaseOrder> findAll();
}
