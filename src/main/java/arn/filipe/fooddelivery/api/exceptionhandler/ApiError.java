package arn.filipe.fooddelivery.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiError {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "https://HelpperPageAboutException.com.br/invalid-data", position = 5)
    private String type;

    @ApiModelProperty(example = "Invalid data", position = 10)
    private String title;

    @ApiModelProperty(example = "One or more fields are invalid. Fix it and continue.", position = 15)
    private String detail;

    @ApiModelProperty(example = "An unexpected internal system error has occurred. " +
            "Please try again and if the problem persists, contact your system administrator.", position = 20)
    private String userMessage;

    @ApiModelProperty(example = "2021-08-26T15:21:25.497499302Z", position = 25)
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "Object of fields that generated the error (optional)", position = 30)
    private List<Object> objects;

    @ApiModel("Error")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "price")
        private String name;

        @ApiModelProperty(example = "Price is required")
        private String userMessage;
    }

}
