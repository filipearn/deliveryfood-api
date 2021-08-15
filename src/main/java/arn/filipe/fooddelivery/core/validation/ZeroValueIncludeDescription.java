package arn.filipe.fooddelivery.core.validation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ZeroValueIncludeDescriptionValidator.class })
public @interface ZeroValueIncludeDescription {
    java.lang.String message() default "required description invalid";

    java.lang.Class<?>[] groups() default {};

    java.lang.Class<? extends javax.validation.Payload>[] payload() default {};

    String valueField();

    String descriptionField();

    String requiredDescription();
}
