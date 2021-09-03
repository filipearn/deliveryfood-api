package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends CustomizedJpaRepository<PurchaseOrder, Long>,
        JpaSpecificationExecutor<PurchaseOrder> {

    @Query("from PurchaseOrder p join fetch p.client join fetch p.restaurant r join fetch r.kitchen")
    List<PurchaseOrder> findAll();

    Optional<PurchaseOrder> findByCode(String code);

    boolean isPurchaseOrderManagedBy(String code, Long userId);
}
