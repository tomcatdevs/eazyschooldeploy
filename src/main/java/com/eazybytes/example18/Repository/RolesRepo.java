package com.eazybytes.example18.Repository;

import com.eazybytes.example18.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@Repository
@RepositoryRestResource(path = "roles")
public interface RolesRepo extends JpaRepository<Roles,Integer> {
    Roles getByRoleName(String roleName);
}