package com.vbt.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.vbt.kanban.dto.EmailContentDto;

@Service
@EnableAsync
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Async
	public void send(EmailContentDto emailContent) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailContent.getTo());
		mailMessage.setSubject(emailContent.getSubject());
		mailMessage.setFrom(emailContent.getFrom());
		mailMessage.setText(emailContent.getMessage());

		javaMailSender.send(mailMessage);
	}
}
