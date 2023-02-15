package com.saferent.controller;

import com.saferent.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contactmessage")
public class ContactMessageController {

    //@Autowired
    private final ContactMessageService contactMessageService;

    //@Autowired
    public ContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }
}
