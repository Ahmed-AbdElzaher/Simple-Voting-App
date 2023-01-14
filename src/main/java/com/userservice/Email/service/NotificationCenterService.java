package com.userservice.Email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.Email.dto.EmailDto;
import com.userservice.Email.dto.MailResponse;

@Service
public class NotificationCenterService {

    @Autowired
	private EmailService emailService;

    public MailResponse sendEmail(EmailDto emailDto) {
      return emailService.sendEmail(emailDto);
    }

}
