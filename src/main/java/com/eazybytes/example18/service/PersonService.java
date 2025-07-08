package com.eazybytes.example18.service;

import com.eazybytes.example18.Model.Person;
import com.eazybytes.example18.Model.Roles;
import com.eazybytes.example18.Repository.PersonRepo;
import com.eazybytes.example18.Repository.RolesRepo;
import com.eazybytes.example18.constants.EazySchoolConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private RolesRepo rolesRepo;

    public Boolean registerPerson(Person person) {
        boolean isRegister=false;
//        Person registerPerson=personRepo.save(person);
//        return isRegister=true;

        Roles role=rolesRepo.getByRoleName(EazySchoolConstants.STUDENT_ROLE);
        person.setRoles(role);

        person.setPassword(passwordEncoder.encode(person.getPassword()));

        person=personRepo.save(person);
        if (person.getPersonId()>0) {
            isRegister=true;
        }
        return isRegister;
    }
}
