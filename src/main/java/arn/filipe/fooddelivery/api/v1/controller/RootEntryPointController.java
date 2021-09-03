package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
import arn.filipe.fooddelivery.core.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private Security security;

    @GetMapping
    public RootEntryPointModel root(){
        var rootEntryPointModel = new RootEntryPointModel();

        if(security.canFindKitchens()){
            rootEntryPointModel.add(buildLinks.linkToKitchen("kitchens"));
        }

        if(security.canFindPurchaseOrders()){
            rootEntryPointModel.add(buildLinks.linkToPurchaseOrder("purchase-orders"));
        }

        if(security.canFindRestaurants()){
            rootEntryPointModel.add(buildLinks.linkToRestaurant("restaurants"));
        }

        if(security.canEditUsersTeamsPermissions()){
            rootEntryPointModel.add(buildLinks.linkToTeam("teams"));
            rootEntryPointModel.add(buildLinks.linkToUser("users"));
            rootEntryPointModel.add(buildLinks.linkToPermission("permissions"));
        }

        if(security.canFindPaymentWays()){
            rootEntryPointModel.add(buildLinks.linkToPaymentWay("payment-ways"));
        }

        if(security.canFindCities()){
            rootEntryPointModel.add(buildLinks.linkToState("states"));
        }

        if(security.canFindStates()){
            rootEntryPointModel.add(buildLinks.linkToCity("cities"));
        }

        if(security.canFindStatistics()) {
            rootEntryPointModel.add(buildLinks.linkToStatistics("statistics"));
        }

        return rootEntryPointModel;
    }
    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{

    }
}
