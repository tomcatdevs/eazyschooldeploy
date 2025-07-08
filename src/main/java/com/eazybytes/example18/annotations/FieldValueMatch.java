package com.eazybytes.example18.annotations;

import com.eazybytes.example18.validations.FieldValueMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Constraint(validatedBy = FieldValueMatchValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldValueMatch {

    String message() default "Field values does'nt match.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String field();
    String fieldMatch();

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        FieldValueMatch[] value();
    }
}
