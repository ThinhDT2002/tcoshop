package com.tcoshop.api;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.MailInformation;
import com.tcoshop.service.MailService;

@RestController
@CrossOrigin("*")
public class EmailAPI {
    @Autowired
    MailService mailService;
    @PostMapping("/api/email/send")
    public MailInformation send(@RequestBody MailInformation mailInformation) throws MessagingException {
        mailService.send(mailInformation);
        return mailInformation;
    }
}
