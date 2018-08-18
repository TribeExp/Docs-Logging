package com.basakdm.excartest.service.service_impl;

import com.basakdm.excartest.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendRegistrationMessage(String email, String password) {
        log.info("sendRegistrationMessage()");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Car Sharing Team");
        message.setText("Hello! Thanks for registration \n Your login: " + email +
                "\n Your password: "+ password);
        try {
            javaMailSender.send(message);
            log.info("try send message");
            return true;
        } catch (MailException e){
            e.printStackTrace();
            return false;
        }
    }
}
