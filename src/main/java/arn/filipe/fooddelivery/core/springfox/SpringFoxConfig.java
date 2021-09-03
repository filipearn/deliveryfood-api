package arn.filipe.fooddelivery.core.springfox;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.v1.model.*;
import arn.filipe.fooddelivery.api.v1.openapi.model.*;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocketV1(){
        var typeResolver = new TypeResolver() ;

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("arn.filipe.fooddelivery.api"))
                    .paths(PathSelectors.ant("/api/v1/**"))
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//                .globalOperationParameters(Arrays.asList(
//                        new ParameterBuilder()
//                                .name("fields")
//                                .description("Properties name to filter the answer, separated by comma")
//                                .parameterType("query")
//                                .modelRef(new ModelRef("string"))
//                                .build()
//                ))
                .additionalModels(typeResolver.resolve(ApiError.class))
                .ignoredParameterTypes(ServletWebRequest.class)

                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))


                .apiInfo(apiInfoV1())
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(alternateTypeRules())
                .tags(new Tag("Cities", "Manage the cities"),
                        new Tag("Teams","Manage the teams"),
                        new Tag("Kitchens", "Manage the kitchens"),
                        new Tag("Payment ways", "Manage the payment ways"),
                        new Tag("Purchase orders", "Manage the purchase orders"),
                        new Tag("Restaurants", "Manage the restaurants"),
                        new Tag("States", "Manage the states"),
                        new Tag("Products", "Manage the restaurants' products"),
                        new Tag("Users", "Manage the users"),
                        new Tag("Statistics", "Manage the statistics"),
                        new Tag("Permissions", "Manage the permissions"));

    }

    @Bean
    public Docket apiDocketV2(){
        var typeResolver = new TypeResolver() ;

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("arn.filipe.fooddelivery.api"))
                .paths(PathSelectors.ant("/api/v2/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//                .globalOperationParameters(Arrays.asList(
//                        new ParameterBuilder()
//                                .name("fields")
//                                .description("Properties name to filter the answer, separated by comma")
//                                .parameterType("query")
//                                .modelRef(new ModelRef("string"))
//                                .build()
//                ))
                .additionalModels(typeResolver.resolve(ApiError.class))
                .ignoredParameterTypes(ServletWebRequest.class)
                .apiInfo(apiInfoV2())
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .tags(new Tag("Cities", "Manage the cities"),
                        new Tag("Teams","Manage the teams"),
                        new Tag("Kitchens", "Manage the kitchens"),
                        new Tag("Payment ways", "Manage the payment ways"),
                        new Tag("Purchase orders", "Manage the purchase orders"),
                        new Tag("Restaurants", "Manage the restaurants"),
                        new Tag("States", "Manage the states"),
                        new Tag("Products", "Manage the restaurants' products"),
                        new Tag("Users", "Manage the users"),
                        new Tag("Statistics", "Manage the statistics"),
                        new Tag("Permissions", "Manage the permissions"));

    }

    private SecurityScheme securityScheme(){
        return new OAuthBuilder()
                .name("FoodDelivery")
                .grantTypes(grandTypes())
                .scopes(scopes())
                .build();
    }

    public SecurityContext securityContext(){
        var securityReference = SecurityReference.builder()
                .reference("FoodDelivery")
                .scopes(scopes().toArray(new AuthorizationScope[0]))
                .build();

        return SecurityContext.builder()
                .securityReferences(Arrays.asList(securityReference))
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<GrantType> grandTypes(){
        return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
    }

    private List<AuthorizationScope> scopes(){
        return Arrays.asList(
                new AuthorizationScope("READ", "Read access"),
                new AuthorizationScope("WRITE", "Write access"));
    }

    private List<ResponseMessage> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid request (client error)")
                        .responseModel(new ModelRef("ApiError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error")
                        .responseModel(new ModelRef("ApiError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Refused request: format body is not supported")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Resource don't have representation that can be allowed by the consumer")
                        .build()
        );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.CONFLICT.value())
                        .message("Resource in use")
                        .responseModel(new ModelRef("ApiError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid request (client error)")
                        .responseModel(new ModelRef("ApiError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error")
                        .responseModel(new ModelRef("ApiError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Resource don't have representation that can be allowed by the consumer")
                        .build()
        );
    }

    private List<ResponseMessage> globalGetResponseMessages(){
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error")
                        .responseModel(new ModelRef("ApiError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Resource don't have representation that can be allowed by the consummer")
                        .build()
        );
    }

    private ApiInfo apiInfoV1(){
        return new ApiInfoBuilder()
                .title("Food Delivery API")
                .description("OPEN API FOR CLIENTS AND RESTAURANTS")
                .version("1")
                .contact(new Contact("FILIPE", "www.pagodedosinal.com.br", "contato@filipearn.com.br"))
                .build();
    }

    private ApiInfo apiInfoV2(){
        return new ApiInfoBuilder()
                .title("Food Delivery API")
                .description("OPEN API FOR CLIENTS AND RESTAURANTS")
                .version("2")
                .contact(new Contact("FILIPE", "www.pagodedosinal.com.br", "contato@filipearn.com.br"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private AlternateTypeRule[] alternateTypeRules() {
        TypeResolver typeResolver = new TypeResolver();

        return Arrays.asList(
                AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PagedModel.class),
                        KitchensModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PurchaseOrderSummaryModel.class),
                        PurchaseOrdersSummaryModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PurchaseOrderModel.class),
                        PurchaseOrdersModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CityModel.class),
                        CitiesModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, StateModel.class),
                        StatesModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PaymentWayModel.class),
                        PaymentWaysModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, TeamModel.class),
                        TeamsModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissionModel.class),
                        PermissionsModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProductModel.class),
                        ProductsModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestaurantModel.class),
                        RestaurantsModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestaurantSummaryModel.class),
                        RestaurantsSummaryModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestaurantOnlyNameModel.class),
                        RestaurantsOnlyNameModelOpenApi.class
                ),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UserModel.class),
                        UsersModelOpenApi.class
                )
        ).toArray(new AlternateTypeRule[0]);
    }
}
