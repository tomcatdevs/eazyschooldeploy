package com.eazybytes.example18.rest;

import com.eazybytes.example18.Model.Contact;
import com.eazybytes.example18.Model.Response;
import com.eazybytes.example18.Repository.ContactSaveRepository;
import com.eazybytes.example18.constants.EazySchoolConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/contact",produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
})
@CrossOrigin("*")
public class ContactRestApiController {

    @Autowired
    private ContactSaveRepository contactSaveRepository;

    @GetMapping("/getMessages")
    public List<Contact> getMessagesByStatus(@RequestParam("status") String status){
        return contactSaveRepository.findByStatus(status);
    }

    @GetMapping("/getAllMsgByStatusJSON")
    public List<Contact> getAllMsgByStatusJSON(@RequestHeader("invocationHeader") String invocationHeader,
                                               @RequestBody Contact contact){
        log.info("invocationHeader :: "+invocationHeader);
        return contactSaveRepository.findByStatus(contact.getStatus());
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsgByRequestBody(@RequestHeader("invocationHeader") String invocationHeader,
                                                         @Valid @RequestBody Contact contact) {
        Response response = new Response();
        System.out.println("invocationHeader ==> "+invocationHeader);
        response.setStatusCode("200");
        response.setApiResponseMessage("your query added successfully");
        contactSaveRepository.save(contact);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved","SAVE HO GAYA BHAII AB KYA JAAN LEGA API KI".toLowerCase(Locale.ROOT))
                .body(response);
    }

    @DeleteMapping("/deleteMessage")
    public ResponseEntity<Response> deleteMessageById(RequestEntity<Contact> contactRequestEntity){
        Response response = new Response();
        Optional<Contact> contact = contactSaveRepository.findById(contactRequestEntity.getBody().getContactId());
        if (contact.isPresent()){
            response.setStatusCode("200");
            response.setApiResponseMessage("your active query message deleted successfully");
            contactSaveRepository.deleteById(contact.get().getContactId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        else {
            response.setStatusCode("404");
            response.setApiResponseMessage("query message id not found in database");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }

    @PutMapping("/updateMessage1")
    public ResponseEntity<Response> completeUpdateByPut(@Valid @RequestBody Contact contact){
        Response response = new Response();
        Optional<Contact> contactEntity = contactSaveRepository.findById(contact.getContactId());
        if (contactEntity.isPresent()){
            response.setStatusCode("200");
            response.setApiResponseMessage("your active query updated successfully");
            contactSaveRepository.save(contact);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }
        else {
            response.setStatusCode("404");
            response.setApiResponseMessage("query message you want to update is not found in database");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PatchMapping("/closeStatus")
    public ResponseEntity<Response> partialUpdateClosedStatusByPatch(@RequestBody Contact contact){
        Response response=new Response();
        Optional<Contact> contactEntity = contactSaveRepository.findById(contact.getContactId());
        if (contactEntity.isPresent()){
            response.setStatusCode("200");
            contactEntity.get().setStatus(EazySchoolConstants.CLOSE);
            contactSaveRepository.save(contactEntity.get());
            response.setApiResponseMessage("your active query message closed now");
        }
        else {
            response.setStatusCode("404");
            response.setApiResponseMessage("your query message id not found in database");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PatchMapping("/openStatus")
    public ResponseEntity<Response> partialUpdateOpenedStatusByPatch(@RequestBody Contact contact){
        Response response=new Response();
        Optional<Contact> contactEntity = contactSaveRepository.findById(contact.getContactId());
        if (contactEntity.isPresent()){
            response.setStatusCode("200");
            contactEntity.get().setStatus(EazySchoolConstants.OPEN);
            contactSaveRepository.save(contactEntity.get());
            response.setApiResponseMessage("your query message re-opened now and status set to be open");
        }
        else {
            response.setStatusCode("404");
            response.setApiResponseMessage("your query message id not found in database");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
