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

    public boolean isUserAuthenticatedEqualsTo(Long userId) {
        return getUserId() != null && userId != null
                && getUserId().equals(userId);
    }

    public boolean hasAuthority(String authorityName){
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }

    public boolean canManagePurchaseOrders(String code){
        return hasAuthority("SCOPE_WRITE") && (hasAuthority("MANAGE_PURCHASE_ORDERS")
                || managePurchaseOrderRestaurant(code));
    }

    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    public boolean hasWriteScope() {
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean hasReadScope() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean canFindRestaurants() {
        return hasReadScope() && isAuthenticated();
    }

    public boolean canManageRestaurantRegistration() {
        return hasWriteScope() && hasAuthority("EDIT_RESTAURANTS");
    }

    public boolean canManageRestaurantsOperation(Long restaurantId) {
        return hasWriteScope() && (hasAuthority("EDIT_RESTAURANTS")
                || manageRestaurant(restaurantId));
    }

    public boolean canFindUsersTeamsPermissions() {
        return hasReadScope() && hasAuthority("FIND_USERS_TEAMS_PERMISSIONS");
    }

    public boolean canEditUsersTeamsPermissions() {
        return hasWriteScope() && hasAuthority("EDIT_USERS_TEAMS_PERMISSIONS");
    }

    public boolean canFindPurchaseOrders(Long clientId, Long restaurantId) {
        return hasReadScope() && (hasAuthority("FIND_PURCHASE_ORDERS")
                || isUserAuthenticatedEqualsTo(clientId) || manageRestaurant(restaurantId));

    }

    public boolean canFindPurchaseOrders() {
        return isAuthenticated() && hasReadScope();
    }

    public boolean canFindPaymentWays() {
        return isAuthenticated() && hasReadScope();
    }

    public boolean canFindCities() {
        return isAuthenticated() && hasReadScope();
    }

    public boolean canFindStates() {
        return isAuthenticated() && hasReadScope();
    }

    public boolean canFindKitchens() {
        return isAuthenticated() && hasReadScope();
    }

    public boolean canFindStatistics() {
        return hasReadScope() && hasAuthority("GENERATE_REPORTS");
    }



}
