package arn.filipe.fooddelivery.api;

import arn.filipe.fooddelivery.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BuildLinks {

    public static final TemplateVariables PAGINATION_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link linkToPurchaseOrder(Long purchaseOrderId, String rel){

        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clientId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restaurantId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("registrationDateInitial", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("registrationDateFinal", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String purchaseOrdersUrl = linkTo(PurchaseOrderController.class).toUri().toString();

        return new Link(UriTemplate.of(purchaseOrdersUrl, PAGINATION_VARIABLES.concat(filterVariables)), rel);
    }

    public Link linkToPurchaseOrderConfirmation(String code, String rel){
        return linkTo(methodOn(OrderFlowController.class)
                .confirmation(code)).withRel(rel);
    }

    public Link linkToPurchaseOrderCancellation(String code, String rel){
        return linkTo(methodOn(OrderFlowController.class)
                .cancellation(code)).withRel(rel);
    }

    public Link linkToPurchaseOrderDelivery(String code, String rel){
        return linkTo(methodOn(OrderFlowController.class)
                .delivery(code)).withRel(rel);
    }

    public Link linkToPurchaseOrder(Long purchaseOrderId){
        return linkToPurchaseOrder(purchaseOrderId, IanaLinkRelations.SELF.value());
    }

    public Link linkToPurchaseOrder(){
        return linkTo(PurchaseOrderController.class).withSelfRel();
    }


    public Link linkToRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .findById(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurant(String rel) {
        TemplateVariables projectionVariables = new TemplateVariables(
                new TemplateVariable("projection", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String restaurantUrl = linkTo(RestaurantController.class).toUri().toString();

        return new Link(UriTemplate.of(restaurantUrl, projectionVariables), rel);
    }

    public Link linkToRestaurant(Long restaurantId) {
        return linkToRestaurant(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantActivation(Long restaurantId, String rel){
        return linkTo(methodOn(RestaurantController.class)
                .activate(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantDeactivation(Long restaurantId, String rel){
        return linkTo(methodOn(RestaurantController.class)
                .deactivate(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantOpening(Long restaurantId, String rel){
        return linkTo(methodOn(RestaurantController.class)
                .opening(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantClosure(Long restaurantId, String rel){
        return linkTo(methodOn(RestaurantController.class)
                .closure(restaurantId)).withRel(rel);
    }

    public Link linkToKitchen(Long kitchenId, String rel) {
        return linkTo(methodOn(KitchenController.class)
                .findById(kitchenId)).withRel(rel);
    }

    public Link linkToKitchen(String rel) {
        return linkTo(KitchenController.class).withRel(rel);
    }

    public Link linkToKitchen() {
        return linkToKitchen(IanaLinkRelations.SELF.value());
    }

    public Link linkToUser(Long userId, String rel) {
        return linkTo(methodOn(UserController.class)
                .findById(userId)).withRel(rel);
    }

    public Link linkToUser(Long userId) {
        return linkToUser(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUser(String rel) {
        return linkTo(UserController.class).withRel(rel);
    }

    public Link linkToTeamUser(Long userId, String rel) {
        return linkTo(methodOn(UserTeamController.class)
                .listAll(userId)).withRel(rel);
    }

    public Link linkToTeamUser(Long userId) {
        return linkToTeamUser(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToTeamUserAssociation(Long userId, String rel){
        return linkTo(methodOn(UserTeamController.class)
                .associate(userId, null)).withRel(rel);
    }

    public Link linkToTeamUserDisassociation(Long userId, Long teamId, String rel){
        return linkTo(methodOn(UserTeamController.class)
                .disassociate(userId, teamId)).withRel(rel);
    }

    public Link linkToKitchen(Long kitchenId) {
        return linkToKitchen(kitchenId, IanaLinkRelations.SELF.value());
    }

    public Link linkToResponsibleRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantUserController.class)
                .listAll(restaurantId)).withRel(rel);
    }

    public Link linkToResponsibleRestaurantAssociate(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantUserController.class)
                .associateUser(restaurantId, null)).withRel(rel);
    }

    public Link linkToResponsibleRestaurantDisassociate(Long restaurantId, Long userId, String rel) {
        return linkTo(methodOn(RestaurantUserController.class)
                .disassociateUser(restaurantId, userId)).withRel(rel);
    }

    public Link linkToPaymentWayRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantPaymentWayController.class)
                .listAll(restaurantId)).withRel(rel);
    }

    public Link linkToPaymentWayRestaurant(Long restaurantId) {
        return linkToPaymentWayRestaurant(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantPaymentWayDisassociation(Long restaurantId, Long paymentWayId, String rel){
        return linkTo(methodOn(RestaurantPaymentWayController.class)
                .disassociate(restaurantId, paymentWayId)).withRel(rel);
    }

    public Link linkToRestaurantPaymentWayAssociation(Long restaurantId, String rel){
        return linkTo(methodOn(RestaurantPaymentWayController.class)
                .associate(restaurantId, null)).withRel(rel);
    }

    public Link linkToCity(Long cityId, String rel) {
        return linkTo(methodOn(CityController.class)
                .findById(cityId)).withRel(rel);
    }

    public Link linkToCity(String rel) {
        return linkTo(CityController.class).withRel(rel);
    }

    public Link linkToCity(Long cityId) {
        return linkToCity(cityId, IanaLinkRelations.SELF.value());
    }

    public Link linkToState(Long stateId, String rel) {
        return linkTo(methodOn(StateController.class)
                .findById(stateId)).withRel(rel);
    }

    public Link linkToState(String rel) {
        return linkTo(StateController.class).withRel(rel);
    }

    public Link linkToPaymentWay(String rel) {
        return linkTo(PaymentWayController.class).withRel(rel);
    }
    public Link linkToPaymentWay(Long paymentWayId, String rel) {
        return linkTo(methodOn(PaymentWayController.class)
                .findById(paymentWayId, null)).withRel(rel);
    }


    public Link linkToRestaurantPaymentWay(Long restaurantId) {
        return linkToPaymentWayRestaurant(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantPaymentWay(String rel) {
        return linkTo(PaymentWayController.class).withRel(rel);
    }


    public Link linkToPaymentWay() {
        return linkToPaymentWay(IanaLinkRelations.SELF.value());
    }

    public Link linkToPaymentWay(Long paymentWayId) {
        return linkToPaymentWay(paymentWayId, IanaLinkRelations.SELF.value());
    }



    public Link linkToRestaurantProducts(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class)
                .listAll(restaurantId, null)).withRel(rel);
    }

    public Link linkToRestaurantProducts(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class)
                .findById(restaurantId, productId)).withRel(rel);
    }

    public Link linkToRestaurantProducts(Long restaurantId, Long productId) {
        return linkTo(methodOn(RestaurantProductController.class)
                .findById(restaurantId, productId)).withRel(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantProducts(Long restaurantId) {
        return linkToRestaurantProducts(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantProductPhoto(Long restaurantId, Long productId, String rel) throws Exception {
        return linkTo(methodOn(RestaurantProductPhotoController.class)
                .find(restaurantId, productId)).withRel(rel);
    }

    public Link linkToRestaurantProductPhoto(Long restaurantId, Long productId) throws Exception {
        return linkToRestaurantProductPhoto(restaurantId, productId, IanaLinkRelations.SELF.value());
    }



    public Link linkToTeam(String rel) {
        return linkTo(TeamController.class).withRel(rel);
    }

    public Link linkToTeam() {
        return linkToTeam(IanaLinkRelations.SELF.value());
    }

    public Link linkToTeam(Long teamId){
        return linkToTeam(teamId, IanaLinkRelations.SELF.value());
    }
    public Link linkToTeam(Long teamId, String rel){
        return linkTo(methodOn(TeamController.class)
                .findById(teamId)).withRel(rel);
    }

    public Link linkToTeamPermission(Long teamId){
        return linkToTeamPermission(teamId, IanaLinkRelations.SELF.value());
    }
    public Link linkToTeamPermission(Long teamId, String rel){
        return linkTo(methodOn(TeamPermissionController.class)
                .listAll(teamId)).withRel(rel);
    }

    public Link linkToTeamPermissionDisassociation(Long teamId, Long permissionId, String rel){
        return linkTo(methodOn(TeamPermissionController.class)
                .disassociate(teamId, permissionId)).withRel(rel);
    }

    public Link linkToTeamPermissionAssociation(Long teamId, String rel){
        return linkTo(methodOn(TeamPermissionController.class)
                .associate(teamId, null)).withRel(rel);
    }
}
