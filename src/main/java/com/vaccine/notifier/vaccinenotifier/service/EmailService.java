package com.vaccine.notifier.vaccinenotifier.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.vaccine.notifier.vaccinenotifier.model.Subscriber;
import com.vaccine.notifier.vaccinenotifier.repository.SubscriberRepository;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	SubscriberRepository subscriberRepository;
	
	@Async
	public void sentMailWhenCenterFound(String to, String subject, String body,Subscriber sub) throws MessagingException
	{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setText(body, true);
		
		helper.setTo(to);
        helper.setSubject(subject);
        
        mailSender.send(mimeMessage);
        
        subscriberRepository.save(sub);
	}
	
	@Async
	public void sentMailWhenSubscribed(String to,String subject,String body,Subscriber sub) throws MessagingException
	{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setText(body, true);
		
		helper.setTo(to);
        helper.setSubject(subject);
        
        mailSender.send(mimeMessage);
        
		subscriberRepository.save(sub);
	}
	
}
