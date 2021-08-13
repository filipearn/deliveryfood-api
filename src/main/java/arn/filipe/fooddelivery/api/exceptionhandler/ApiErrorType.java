package arn.filipe.fooddelivery.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {

    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business exception"),
    INVALID_MESSAGE("/invalid-message", "Invalid sent message");

    private String uri;
    private String title;

    ApiErrorType(String path, String title){
        this.uri = "https://HelpperPageAboutException.com.br" + path;
        this.title = title;
    }
}
