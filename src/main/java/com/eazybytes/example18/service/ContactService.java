package com.eazybytes.example18.service;

import com.eazybytes.example18.Model.Contact;
import com.eazybytes.example18.Repository.ContactSaveRepository;
import com.eazybytes.example18.config.EazySchoolConfigProps;
import com.eazybytes.example18.constants.EazySchoolConstants;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Data
@Service
//@RequestScope
//@SessionScope
public class ContactService {

    private static Logger log= LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactSaveRepository contactSaveRepository;
    private int contactId;

    @Autowired
    private EazySchoolConfigProps eazySchoolConfigProps;

//    private int counter=0;

//    public contactService(){
//        System.out.println("contactService Bean Initialized.....");
//    }

    public boolean savedContactToDB(Contact contact){
        boolean isSaved = false;
        contact.setStatus(EazySchoolConstants.OPEN);

        Contact savingContact=contactSaveRepository.save(contact);
        if (savingContact.getContactId()>0) {
            isSaved=true;
        }
        return isSaved;
    }

//    public ArrayList<Contact> findAllOpenQueryMessages(){
////        return contactSaveRepository.findAllOpenQueryMessages(EazySchoolConstants.OPEN);
//
////     finding by mobile number only
////        return contactSaveRepository.findByMobileNum("9512345678");
//
////        finding contact person by email only
////        return contactSaveRepository.findByEmail("aman@gmail.com");
//
//
////        Fetch data based on email and mobile number.
////       findByEmailAndMobile
////    return contactSaveRepository.findByEmailAndMobileNum("ayush@gmail.com","9512345678");
//
//        //    FETCHING DATA USING DERIVED OR CUSTOM CREATED FUNCTION OF DATA JPA
////        finding by message query status
//        return  contactSaveRepository.findByStatus(EazySchoolConstants.OPEN);
//    }

//    public Boolean updateMsgStatus(int contactId){
//        boolean isUpdated=false;
//
////       fetching existing record from DB for doing updation
//        Optional<Contact> contact=contactSaveRepository.findById(contactId);
//
////        updating the status if object data is present inside Optional
//        contact.ifPresent(contact1 -> {
//            contact1.setStatus(EazySchoolConstants.CLOSE);
//        });
////       again saving newly updated data inside database based on contact detail available inside Optional
//        Contact updatedContact=contactSaveRepository.save(contact.get());
//
////       checking either updateContact object is blank or having some data inside.
//        if (updatedContact.getUpdatedBy() != null){
//            isUpdated=true;
//        }
//        return isUpdated;
//    }

    public Page<Contact> findAllOpenQueryMessages(int pageNum, String sortField, String sortDir) {
//        int pageSize = 5;
        int pageSize = eazySchoolConfigProps.getPageSize();
        if (null!=eazySchoolConfigProps.getContact() && null!=eazySchoolConfigProps.getContact().get("pageSize")){
            pageSize = Integer.parseInt(eazySchoolConfigProps.getContact().get("pageSize").trim());
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        return contactSaveRepository.findByStatus_NamedQuery(
                EazySchoolConstants.OPEN,pageable);
    }

    public Boolean updateMsgStatus(int contactId) {
        boolean isUpdated=false;
        int rowsEffected = contactSaveRepository.updateByStatus(EazySchoolConstants.CLOSE, contactId);
        if (rowsEffected > 0){
            isUpdated=true;
        }
        return isUpdated;
    }
}
