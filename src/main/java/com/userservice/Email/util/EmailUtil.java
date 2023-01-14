package com.userservice.Email.util;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.userservice.Email.dto.EmailConfiguration;


@Component
public class EmailUtil {
    
    public static Properties initJavaMailProperties(){
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        return javaMailProperties;
    }

    public static JavaMailSenderImpl initMailSender(EmailConfiguration emailConfigObject){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfigObject.getHost());
        mailSender.setPort(emailConfigObject.getPort());
        mailSender.setUsername(emailConfigObject.getUsername());
        mailSender.setPassword(emailConfigObject.getPassword());
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setProtocol("smtp");

        return mailSender;
    }

}
