package com.eazybytes.example18.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import java.util.Set;

//@Data
@Getter
@Setter
@Entity
@Table(name = "class")
public class EazyClass extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int classId;

    @NotBlank(message = "Class name must not be blank")
    @Size(min = 3,message = "Class name must be at least of 3 characters")
    private String name;

    @OneToMany(mappedBy = "eazyClass",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,targetEntity = Person.class)
    private Set<Person> persons;
}
