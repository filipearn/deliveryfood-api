package arn.filipe.fooddelivery.infrastructure.service.query;

import arn.filipe.fooddelivery.domain.enums.OrderStatus;
import arn.filipe.fooddelivery.domain.filter.DailySaleFilter;
import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import arn.filipe.fooddelivery.domain.model.dto.DailySale;
import arn.filipe.fooddelivery.domain.service.SaleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

    @Autowired
    private EntityManager manager;

    @Override
    public List<DailySale> findDailySales(DailySaleFilter dailySaleFilter, String timeOffset) {
        var builder = manager.getCriteriaBuilder();

        var query = builder.createQuery(DailySale.class);

        var root = query.from(PurchaseOrder.class);

        var predicates = new ArrayList<Predicate>();

        if(dailySaleFilter.getRestaurantId() != null){
            predicates.add(builder.equal(root.get("restaurant"), dailySaleFilter.getRestaurantId()));
        }

        if(dailySaleFilter.getRegistrationDateInitial() != null){
            predicates.add(builder.equal(root.get("registrationDate"), dailySaleFilter.getRegistrationDateInitial()));
        }

        if(dailySaleFilter.getRegistrationDateFinal() != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("registrationDate"), dailySaleFilter.getRegistrationDateFinal()));
        }

        predicates.add(root.get("status").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

        var functionConvertTZDateRegistration = builder.function(
                "convert_tz", Date.class, root.get("registrationDate"), builder.literal("+00:00"), builder.literal(timeOffset));

        var functionDateRegistration = builder.function(
                "date", Date.class, functionConvertTZDateRegistration);

        var selection = builder.construct(DailySale.class,
                functionDateRegistration,
                builder.count(root.get("id")),
                builder.sum(root.get("totalValue")));

        query.select(selection).where(builder.and(predicates.toArray(new Predicate[0])));

        query.groupBy(functionDateRegistration);

        return manager.createQuery(query).getResultList();
    }
}
