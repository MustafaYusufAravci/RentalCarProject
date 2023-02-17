package com.saferent.controller;

import com.saferent.domain.ContactMessage;
import com.saferent.dto.ContactMessageDTO;
import com.saferent.dto.request.ContactMessageRequest;
import com.saferent.dto.response.SfResponse;
import com.saferent.mapper.ContactMessageMapper;
import com.saferent.service.*;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contactmessage")
public class ContactMessageController {

    //@Autowired // field injection yapmamak icin commente alindi
    private final ContactMessageService contactMessageService;

    private final ContactMessageMapper contactMessageMapper;

    //@Autowired
    public ContactMessageController(ContactMessageService contactMessageService, ContactMessageMapper contactMessageMapper) {
        this.contactMessageService = contactMessageService;
        this.contactMessageMapper = contactMessageMapper;
    }

    //!!! create ContactMessage
    @PostMapping("/visitors")
    public ResponseEntity<SfResponse> createMessage(@Valid @RequestBody ContactMessageRequest contactMessageRequest){
        // bana gelen DTO yu POJO ya cevirmek icin mapStruct yapisini kullanacagim
        ContactMessage contactMessage =
                contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);
        contactMessageService.saveMessage(contactMessage);
        SfResponse response = new SfResponse("ContactMessage successfully created", true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    //!!! getaLL ContactMessages
    @GetMapping
    public ResponseEntity<List<ContactMessageDTO>> getAllContactMessage() {
        List<ContactMessage> contactMessageList =  contactMessageService.getAll();
        //mapStruct ( POJOs -> DTOs )
        List<ContactMessageDTO> contactMessageDTOList = contactMessageMapper.map(contactMessageList);

        return ResponseEntity.ok(contactMessageDTOList); //   return new ResponseEntity<>(contactMessageDTOList, HttpStatus.OK);

    }

    //!!! // pageable ( Server Side Paging - Client Side Pacing )
    @GetMapping("/pages")
    public ResponseEntity<Page<ContactMessageDTO>> getAllContactMessageWithPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop, // neye gore siralanacagini belirtiyoruz
            @RequestParam(value="direction", required = false, defaultValue = "DESC")Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,prop));
        Page<ContactMessage> contactMessagePage = contactMessageService.getAll(pageable);
        Page<ContactMessageDTO> pageDTO = getPageDTO(contactMessagePage);

        return ResponseEntity.ok(pageDTO);
    }


    //!!! spesifik olarak bir ContactMessage PathVariable ile alalim]
    @GetMapping("/{id}")
    public ResponseEntity<ContactMessageDTO> getMessageWithPath(@PathVariable("id") Long id){

        ContactMessage contactMessage = contactMessageService.getContactMessage(id);

        ContactMessageDTO contactMessageDTO = contactMessageMapper.contactMessageToDTO(contactMessage);

        return ResponseEntity.ok(contactMessageDTO);

    }

    //!!! spesifik olarak bir ContactMessage RequestParam ile alalim
    @GetMapping("/request")
    public ResponseEntity<ContactMessageDTO> getMessageWithRequestParam(
                                        @RequestParam("id") Long id){
        ContactMessage contactMessage = contactMessageService.getContactMessage(id);
        ContactMessageDTO contactMessageDTO = contactMessageMapper.contactMessageToDTO(contactMessage);

        return ResponseEntity.ok(contactMessageDTO);

    }


    //!!! getPageDTO
    private Page<ContactMessageDTO> getPageDTO(Page<ContactMessage> contactMessagePage){

        return contactMessagePage.map( // map methodu Page yapisindan geliyor
                contactMessage -> contactMessageMapper.contactMessageToDTO(contactMessage));

    }



}
