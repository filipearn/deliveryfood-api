package arn.filipe.fooddelivery.core.springfox;

import arn.filipe.fooddelivery.api.exceptionhandler.ApiError;
import arn.filipe.fooddelivery.api.model.KitchenModel;
import arn.filipe.fooddelivery.api.openapi.model.KitchensModelOpenApi;
import arn.filipe.fooddelivery.api.openapi.model.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket(){
        var typeResolver = new TypeResolver() ;

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("arn.filipe.fooddelivery.api"))
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
                .apiInfo(apiInfo())
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, KitchenModel.class),
                        KitchensModelOpenApi.class))
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

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Food Delivery API")
                .description("OPEN API FOR CLIENTS AND RESTAURANTS")
                .version("1")
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
}
