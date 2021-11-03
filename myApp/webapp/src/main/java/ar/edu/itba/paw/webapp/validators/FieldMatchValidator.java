package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.FieldMatch;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>
{
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation)
    {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try
        {
            PropertyDescriptor propertyDescriptor = BeanUtils
                    .getPropertyDescriptor(value.getClass(), firstFieldName);
            final Object firstObj = propertyDescriptor.getReadMethod().invoke(value);
            propertyDescriptor = BeanUtils
                    .getPropertyDescriptor(value.getClass(), secondFieldName);
            final Object secondObj = propertyDescriptor.getReadMethod().invoke(value);

            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {
            // ignore
        }
        return true;
    }
}
