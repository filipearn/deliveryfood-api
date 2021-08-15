package arn.filipe.fooddelivery.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;


public class ZeroValueIncludeDescriptionValidator implements ConstraintValidator<ZeroValueIncludeDescription, Object> {

    private String valueField;
    private String descriptionField;
    private String requiredField;
    @Override
    public void initialize(ZeroValueIncludeDescription constraintAnnotation) {
        this.valueField = constraintAnnotation.valueField();
        this.descriptionField = constraintAnnotation.descriptionField();
        this.requiredField = constraintAnnotation.requiredDescription();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(o.getClass(), valueField)
                    .getReadMethod().invoke(o);

            String description = (String) BeanUtils.getPropertyDescriptor(o.getClass(), descriptionField)
                    .getReadMethod().invoke(o);

            if(value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null){
                valid = description.toLowerCase().contains(this.requiredField.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return valid;
    }
}
