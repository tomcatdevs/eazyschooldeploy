package com.eazybytes.example18.Repository;

import com.eazybytes.example18.Model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RepositoryRestResource(path = "courses")
//@RepositoryRestResource(exported = false)
public interface CoursesRepository extends JpaRepository<Courses,Integer> {
    ArrayList<Courses> findByOrderByNameDesc();
    ArrayList<Courses> findByOrderByName();
}
