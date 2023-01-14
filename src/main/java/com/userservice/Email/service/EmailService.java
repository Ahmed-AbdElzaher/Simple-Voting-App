package com.userservice.Email.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.userservice.Email.dto.EmailDto;
import com.userservice.Email.dto.MailResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	
	public MailResponse sendEmail(EmailDto emailDto) {

		Map<String, Object> htmlEmailTemplateModel = new HashMap<>();
		htmlEmailTemplateModel.put("message", emailDto.getEmailMessage());

		MailResponse response = new MailResponse();
		MimeMessage mimeMessage = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Template template = config.getTemplate("email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, htmlEmailTemplateModel);

			helper.setTo(emailDto.getEmailTo());
			helper.setText(html, true);
			helper.setSubject(emailDto.getEmailSubject());
			helper.setFrom(emailDto.getEmailFrom().emailFrom);
			sender.send(mimeMessage);

			response.setMessage("mail send to : " + emailDto.getEmailTo());
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException ex) {
			response.setMessage("Mail Sending failure : "+ex.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}

}
