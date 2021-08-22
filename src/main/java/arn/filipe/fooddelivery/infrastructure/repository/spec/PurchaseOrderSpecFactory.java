package arn.filipe.fooddelivery.infrastructure.repository.spec;

import arn.filipe.fooddelivery.domain.model.PurchaseOrder;
import arn.filipe.fooddelivery.domain.repository.filter.PurchaseOrderFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import javax.persistence.criteria.Predicate;

public class PurchaseOrderSpecFactory {

    public static Specification<PurchaseOrder> usingFilter(PurchaseOrderFilter filter){
        return (root, query, builder) -> {

            root.fetch("restaurant").fetch("kitchen");
            root.fetch("client");
            root.fetch("paymentWay");
            root.fetch("items");

            var predicates = new ArrayList<Predicate>();

            if(filter.getClientId() != null){
                predicates.add(builder.equal(root.get("client"), filter.getClientId()));
            }

            if(filter.getRestaurantId() != null){
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if(filter.getRegistrationDateInitial() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("registrationDate"), filter.getRegistrationDateInitial()));
            }

            if(filter.getRegistrationDateFinal() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("registrationDate"), filter.getRegistrationDateFinal()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };


    }
}
