package com.eazybytes.example18.Repository;

import com.eazybytes.example18.Model.EazyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface eazyClassRepository extends JpaRepository<EazyClass,Integer> {
}
