package arn.filipe.fooddelivery.api.v1.controller;

import arn.filipe.fooddelivery.api.v1.BuildLinks;
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

    @GetMapping
    public RootEntryPointModel root(){
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(buildLinks.linkToKitchen("kitchens"));
        rootEntryPointModel.add(buildLinks.linkToPurchaseOrder("purchase-orders"));
        rootEntryPointModel.add(buildLinks.linkToRestaurant("restaurants"));
        rootEntryPointModel.add(buildLinks.linkToTeam("teams"));
        rootEntryPointModel.add(buildLinks.linkToUser("users"));
        rootEntryPointModel.add(buildLinks.linkToPermission("permissions"));
        rootEntryPointModel.add(buildLinks.linkToPaymentWay("payment-ways"));
        rootEntryPointModel.add(buildLinks.linkToState("states"));
        rootEntryPointModel.add(buildLinks.linkToCity("cities"));
        rootEntryPointModel.add(buildLinks.linkToStatistics("statistics"));

        return rootEntryPointModel;
    }
    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{

    }
}
