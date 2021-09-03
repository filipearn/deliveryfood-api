package arn.filipe.fooddelivery.core.security;

import arn.filipe.fooddelivery.domain.repository.PurchaseOrderRepository;
import arn.filipe.fooddelivery.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class Security {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId(){
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("user_id");
    }

    public boolean manageRestaurant(Long restaurantId){
        if (restaurantId == null) {
            return false;
        }

        return restaurantRepository.existsResponsible(restaurantId, getUserId());
    }

     public boolean managePurchaseOrderRestaurant(String code) {
        return purchaseOrderRepository.isPurchaseOrderManagedBy(code, getUserId());
    }


}
