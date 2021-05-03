package com.vaccine.notifier.vaccinenotifier.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccine.notifier.vaccinenotifier.config.MailTemplate;
import com.vaccine.notifier.vaccinenotifier.model.Subscriber;
import com.vaccine.notifier.vaccinenotifier.repository.SubscriberRepository;


@Service
public class SubscriberService {

	@Autowired
	SubscriberRepository subscriberRepo;
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	private static String WELCOME_SUB = "Thanks for registering with Vaccine-notifier!";
	private static String WELCOME_BODY = "<h2>Hey, Covid Warrior</h2> \n<h4>Now no need to worry about finding your vaccination slot.</h4>";
	
	@Autowired
	EmailService emailService;
	
	public void addSubscriber(Subscriber sub) throws Exception
	{
		if(sub!=null && validateSubscriber(sub))
		{
			sub.setIsActive(true);
			subscriberRepo.save(sub);
			try {
				emailService.sentMail(sub.getEmailId(),WELCOME_SUB , MailTemplate.WELCOME_SUBSCRIBER);
			}
			catch(Exception ex)
			{
				System.out.println("Exception while sending mail in addSubscriber for -:"+sub.getEmailId());
			}
			
		}
		else
		{
			System.out.println("Invalid subscriber");
			throw new Exception("Invalid Suvbscriber");
		}
	}
	
	public void deleteSubscriber(Subscriber sub)
	{
		Optional<Subscriber> subOptional = subscriberRepo.findById(sub.getEmailId());
		if(sub.getEmailId()!=null && subOptional.isPresent() )
		{
			sub = subOptional.get();
			sub.setIsActive(false);
			subscriberRepo.save(sub);
		}
	}
	private boolean validateSubscriber(Subscriber sub)
	{
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(sub.getEmailId());
		if(sub.getEmailId()==null || sub.getDistrictId() ==null || sub.getMinAge() == null || sub.getDistrictId()<=0 || sub.getMinAge()<=0 || !matcher.find())
		{
			return false;
		}
		
		return true;
	}
}
