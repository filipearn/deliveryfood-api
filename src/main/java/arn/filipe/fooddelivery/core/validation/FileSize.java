package arn.filipe.fooddelivery.core.validation;

import javax.validation.Payload;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD,
        java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR,
        java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@javax.validation.Constraint(validatedBy = { FileSizeValidator.class })
public @interface FileSize {

    String message() default "Invalid file size.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String max();
}
