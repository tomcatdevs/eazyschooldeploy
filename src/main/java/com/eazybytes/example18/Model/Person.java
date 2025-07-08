package com.eazybytes.example18.Model;

import com.eazybytes.example18.annotations.FieldValueMatch;
import com.eazybytes.example18.annotations.PasswordValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

//@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldValueMatch.List({
        @FieldValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "E-mail ad/dresses does not matched."),
        @FieldValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Password does not matched.")
})
public class Person extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    private int personId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3,message = "Name must be of at least 3 characters long")
    private String name;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Confirm email must not be blank")
    @Email(message = "Please provide a valid confirm email address")
    @Transient
    @JsonIgnore
    private String confirmEmail;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5,message = "Password must be at least 5 characters long")
    @PasswordValidator
    @JsonIgnore
    private String password;


    @NotBlank(message = "Confirm password must not be blank")
    @Size(min = 5,message = "Confirm password must be at least 5 characters long")
    @Transient
    @JsonIgnore
    private String confirmPassword;

//    PARENT CLASS => PERSON
//    CHILD CLASS => ADDRESS
//    UNI-DIRECTIONAL RELATIONSHIP
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Address.class)
    @JoinColumn(name = "address_id",referencedColumnName = "address_id",nullable = true)
    @JsonIgnore
    private Address address;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,targetEntity = Roles.class)
    @JoinColumn(name = "role_id",referencedColumnName = "role_id",nullable = false)
    private Roles roles;

    @ManyToOne(fetch = FetchType.LAZY,optional =true)
    @JoinColumn(name = "class_id",referencedColumnName = "classId",nullable = true)
    private EazyClass eazyClass;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_courses",
            joinColumns = {@JoinColumn(name = "person_id",referencedColumnName = "personId")},
    inverseJoinColumns = {@JoinColumn(name = "course_id",referencedColumnName = "courseId")})
    private Set<Courses> courses=new HashSet<>();
}
