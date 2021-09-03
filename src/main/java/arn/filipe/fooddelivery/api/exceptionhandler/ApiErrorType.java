package arn.filipe.fooddelivery.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {

    GENERIC_SYSTEM_ERROR("/system-error", "System error"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business exception"),
    INVALID_MESSAGE("/invalid-message", "Invalid sent message"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    DENIED_ACCESS("/denied-access", "Denied access"),
    INVALID_DATA("/invalid-data", "Invalid data");


    private String uri;
    private String title;

    ApiErrorType(String path, String title){
        this.uri = "https://HelpperPageAboutException.com.br" + path;
        this.title = title;
    }
}
