package com.eazybytes.example18.validations;

import com.eazybytes.example18.annotations.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator,String> {

    ArrayList<String> weakPasswords;
    @Override
    public void initialize(PasswordValidator passwordValidator) {
         weakPasswords= new ArrayList<>(Arrays.asList("12345", "password", "qwerty"));
    }

    @Override
    public boolean isValid(String userEnteredPass, ConstraintValidatorContext constraintValidatorContext) {
//        String encoded = new BCryptPasswordEncoder().encode(userEnteredPass);
//        System.out.println("userEnteredPass ENCODED :: "+encoded);
        return userEnteredPass != null && (!weakPasswords.contains(userEnteredPass));
    }
}
