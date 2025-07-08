package com.eazybytes.example18.Repository;

import com.eazybytes.example18.Model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends CrudRepository<Person,Integer> {
    Person readByEmail(String username);
}
