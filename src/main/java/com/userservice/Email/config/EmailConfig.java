package com.userservice.Email.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.userservice.Email.dto.EmailConfiguration;
import com.userservice.Email.util.EmailUtil;

@Configuration
public class EmailConfig {
	
	@Primary
	@Bean 
	public FreeMarkerConfigurationFactoryBean factoryBean() {
		FreeMarkerConfigurationFactoryBean bean=new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("classpath:/templates");
		return bean;
	}

    @Bean
    public JavaMailSender getMailSender() {
        EmailConfiguration emailConfigObject = initEmailConfigObject();
        JavaMailSenderImpl mailSender = EmailUtil.initMailSender(emailConfigObject);
        Properties javaMailProperties = EmailUtil.initJavaMailProperties();
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    private EmailConfiguration initEmailConfigObject(){
		EmailConfiguration emailConfigObject = new EmailConfiguration();
		emailConfigObject.setHost(EmailEnvironment.HOST);
		emailConfigObject.setPort(EmailEnvironment.PORT);
		emailConfigObject.setUsername(EmailEnvironment.USER_NAME);
		emailConfigObject.setPassword(EmailEnvironment.PASSWORD);
		emailConfigObject.setSenderEmail(EmailEnvironment.SENDER_EMAIL);
		return emailConfigObject;
    }
	

}
