package arn.filipe.fooddelivery.core.validation;

import javax.validation.OverridesAttribute;
import javax.validation.constraints.PositiveOrZero;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@javax.validation.Constraint(validatedBy = {})
@PositiveOrZero
public @interface FreighRate {

    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    java.lang.String message() default "{FreighRate.invalid}";

    java.lang.Class<?>[] groups() default {};

    java.lang.Class<? extends javax.validation.Payload>[] payload() default {};

}
