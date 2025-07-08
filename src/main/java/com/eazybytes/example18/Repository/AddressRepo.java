package com.eazybytes.example18.Repository;

import com.eazybytes.example18.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "address")
public interface AddressRepo extends JpaRepository<Address,Integer> {
}
