package com.eazybytes.example18.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "address_id", nullable = false)
    private Long addressId;

    @NotBlank(message = "Primary address can not be blank")
    @Size(min = 8,message = "Primary address must be at least of 8 characters")
    private String address1;

    @NotBlank(message = "Secondary address can not be blank")
    @Size(min = 8,message = "Secondary address must be at least of 8 characters")
    private String address2;

    @NotBlank(message = "City must not be blank")
    @Size(min = 3,message = "City must be at least of 3 characters")
    private String city;

    @NotBlank(message = "State must not be blank")
    @Size(min = 3,message = "State must be at least of 3 characters")
    private String state;

    @NotBlank(message = "Zip Code must not be blank")
    @Size(min = 6,message = "Zip Code must be of 6 characters")
    private String zipCode;
}
