package com.eazybytes.example18.Repository;

import com.eazybytes.example18.Model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ContactSaveRepository extends JpaRepository<Contact,Integer> {

//  Custom/Derived Query Methods
    ArrayList<Contact> findByStatus(String status);
    ArrayList<Contact> findByMobileNum(String mobileNum);
    ArrayList<Contact> findByEmail(String email);
    ArrayList<Contact> findByEmailAndMobileNum(String email,String mobileNum);

//    @Query("SELECT c FROM Contact c WHERE c.status=:status")
//    @Query(value = "SELECT * from contact_msg c where c.status=:status",nativeQuery = true)
//    Page<Contact> findByStatus(String status, Pageable pageable);

    @Transactional
    @Modifying()
    @Query("UPDATE Contact c set c.status=?1 where c.contactId=?2")
    int updateByStatus(@Param("status") String status,int contactId);


//------------------- NAMED QUERY -------------------------------------------
//   SAME DERIVED METHODS FOR @NamedQuery in MODEL CLASSES
    Page<Contact> findByStatus_NamedQuery(String status,Pageable pageable);

    @Transactional
    @Modifying
    int updateByStatusAndContactId_NamedQuery(String status,int contactId);
//------------------- NAMED QUERY -------------------------------------------


//============ NAMED NATIVE QUERIES ============================================
//  FOR @NamedNativeQuery written in model classes
    @Query(nativeQuery = true)
    Page<Contact> findByStatus_NamedNativeQuery(String status,Pageable pageable);

//  FOR @NamedNativeQuery written in model classes
    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateStatusClosedBy_NamedNative(String status,int contactId);
//============ NAMED NATIVE QUERIES ============================================
}
