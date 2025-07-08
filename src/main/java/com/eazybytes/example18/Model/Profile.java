package com.eazybytes.example18.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Profile {

    @NotBlank(message = "Must not be blank")
    @Size(min = 3,message = "Name must be at least of 3 characters")
    private String name;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide valid email address")
    private String email;

    @NotBlank(message = "Address must not be blank")
    @Size(min = 5,message = "Address must be at least of 5 characters.")
    private String address1;

    private String address2;

    @NotBlank(message = "City must not be blank")
    @Size(min = 3,message = "City must be at least of 3 characters.")
    private String city;

    @NotBlank(message = "State must not be blank")
    @Size(min = 3,message = "State must be at least of 3 characters.")
    private String state;

    @NotBlank(message = "Zip Code must not be blank")
    @Size(min = 6,message = "Zip code must be of 6 characters")
    private String zipCode;
}
