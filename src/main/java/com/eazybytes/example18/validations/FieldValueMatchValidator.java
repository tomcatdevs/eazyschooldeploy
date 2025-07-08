package com.eazybytes.example18.validations;

import com.eazybytes.example18.annotations.FieldValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldValueMatchValidator implements ConstraintValidator<FieldValueMatch,Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldValueMatch constraintAnnotation) {
        this.field=constraintAnnotation.field();
        this.fieldMatch=constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue=new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        }
        else {
            return fieldMatchValue == null;
        }

/*
        ENABLED THIS CODE ONLY WHEN
[        SPRING SECURITY MVC + DATA JPA SECURITY CHECK IS ADDED INTO application.properties file. ]
application.properties ::  spring.jpa.properties.javax.persistence.validation.mode=none
*/
//        if (fieldValue != null) {
//            if (fieldValue.toString().startsWith("$2a")){
//                return true;
//            }
//            else {
//                return fieldValue.equals(fieldMatchValue);
//            }
//        }else {
//            return fieldMatchValue==null;
//        }
    }
}
