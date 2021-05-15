package com.vaccine.notifier.vaccinenotifier.schedular;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vaccine.notifier.vaccinenotifier.config.MailTemplate;
import com.vaccine.notifier.vaccinenotifier.dto.Center;
import com.vaccine.notifier.vaccinenotifier.dto.DistinctDistrictAge;
import com.vaccine.notifier.vaccinenotifier.dto.District;
import com.vaccine.notifier.vaccinenotifier.dto.State;
import com.vaccine.notifier.vaccinenotifier.model.Subscriber;
import com.vaccine.notifier.vaccinenotifier.repository.SubscriberRepository;
import com.vaccine.notifier.vaccinenotifier.service.EmailService;
import com.vaccine.notifier.vaccinenotifier.service.VaccineNotifierService;

class StateResponse {
	private List<State> states;

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

}

class DistrictResponse {
	private List<District> districts;

	public List<District> getDistricts() {
		return districts;
	}

	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}

}

@Service
public class VaccineNotifierSchedular {

	@Autowired
	SubscriberRepository subscriberRepository;
	@Autowired
	VaccineNotifierService VaccineNotifierService;

	@Value("${cowin.states.endpoint}")
	private String cowinStatesEndpoint;

	@Value("${cowin.district.endpoint}")
	private String cowinDistrictEndpoint;

	@Value("${vacine-notifier.url}")
	private String vaccineNotifierUrl;
	
	@Value("${email.gap}")
	private int emailGap;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
    private JavaMailSender mailSender;
	
	private static String SLOTS_AVAILABLE_SUB = "Hurry vaccination slots are available near you!!";

	private int count =0;
	
	@Scheduled(cron = "${cron.expression}")
	public void findAvailableCenters() throws ParseException {
		count =0 ;
		if(isNightTime())
		{
			System.out.println("Cant't send email at night time -:"+ new Date());
			return;
		}
		
		Date startDate = this.getMinDate();
		Date endDate = this.getMaxDate();
		
		Set<DistinctDistrictAge> districtAgePairs = subscriberRepository.findDistinctDistricts(startDate,endDate);

		System.out.println("Job started at -: "+new Date());
		if (!CollectionUtils.isEmpty(districtAgePairs)) {
			
			System.out.println("Total distinct district-age pairs are.."+districtAgePairs.size()+" At-: "+new Date());
			
			districtAgePairs.forEach(districAgePair -> {
				Long districtId = districAgePair.getDistrictId();
				Integer minAge = districAgePair.getMinAge();

				List<Center> centers = new ArrayList<>();
				
				try {
					count+=4;
					centers = VaccineNotifierService.getNextAvailableSlots(districtId, minAge,true);
				} catch (URISyntaxException e) {
					System.out.println(
							"getNextAvailableSlots failed for district-:" + districtId + ",ageSlot-:" + minAge);

					e.printStackTrace();
				}
				
				if (!CollectionUtils.isEmpty(centers)) {
					int size = centers.size();
					System.out.println(districtId + " " + minAge + " " + size);
					
					Pageable pageRequest = PageRequest.of(0, 90);
					
					Page<Subscriber> subscribersPage = subscriberRepository.findValidSubscribers(districtId, minAge, startDate, endDate,pageRequest);
					while (!subscribersPage.isEmpty()) {
						
					    List<String> bcc = new ArrayList<String>();
						
						List<Subscriber> subscribers = subscribersPage.getContent();
						
						subscribers.forEach(subscriber -> {
                            bcc.add(subscriber.getEmailId());
						});
						
						try {
							MimeMessage	mimeMessage = createMimeMsg(MailTemplate.getSlotsAvailableMsg(size, getSlotAvailableUrl(subscribers.get(0).getDistrictId(),subscribers.get(0).getStateId(),subscribers.get(0).getMinAge())), SLOTS_AVAILABLE_SUB, bcc);
							emailService.sentMailWhenCenterFound(mimeMessage);
						} catch (MessagingException e) {
							System.out.println("Can't create email message!!");
							e.printStackTrace();
						}
						
						subscribers.forEach(subscriber ->{
							subscriber.setLastNotifiedAt(new Date());
   							subscriberRepository.save(subscriber);
						});
						
						if(subscribersPage.hasNext())
						{
							pageRequest=  subscribersPage.nextPageable();
							subscribersPage = subscriberRepository.findValidSubscribers(districtId, minAge, startDate, endDate,pageRequest);
						}
						else
						{
							subscribersPage = Page.empty();
						}
						
					}
					
				
				}

			});
			
			System.out.println("Job completed at "+new Date()+" with count -:"+count);
		}

	}

	private List<District> getDistricts(Long stateId) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(cowinDistrictEndpoint).path("/" + stateId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");

		HttpEntity<Object> request = new HttpEntity<>(headers);

		String uri = builder.toUriString();

		System.out.println("Calling cowin api -: " + uri);

		try {
			ResponseEntity<DistrictResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
					request, DistrictResponse.class);

			if (response.getBody() != null && (response.getStatusCode().is2xxSuccessful())
					&& response.getBody().getDistricts() != null) {
				return response.getBody().getDistricts();
			}
		} catch (Exception ex) {
			System.out.println("Can't fetch available  districts");
			System.out.println(ex);
		}

		return new ArrayList<District>();

	}

	private List<State> getStates() {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(cowinStatesEndpoint);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");

		HttpEntity<Object> request = new HttpEntity<>(headers);

		String uri = builder.toUriString();

		System.out.println("Calling cowin api -: " + uri);

		try {
			ResponseEntity<StateResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
					request, StateResponse.class);

			if (response.getBody() != null && (response.getStatusCode().is2xxSuccessful())
					&& response.getBody().getStates() != null) {
				return response.getBody().getStates();
			}
		} catch (Exception ex) {
			System.out.println("Can't fetch available districts");
			System.out.println(ex);
		}

		return new ArrayList<State>();
	}

	private Date getTodayDate() throws ParseException {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		Date d = cal.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(formatter.format(d));
	}
	
	private Date getMaxDate()
	{
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.HOUR_OF_DAY, -1*emailGap);
		
		return cal.getTime();
		
	}
	
	private  Date getMinDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2021);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	private String getSlotAvailableUrl(Long districtId,Long stateId,Integer minAge)
	{
	
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(vaccineNotifierUrl)
				.queryParam("districtId", districtId)
				.queryParam("stateId",stateId)
				.queryParam("minAge", minAge);
         
		return builder.toUriString();
	}
	
	private boolean isNightTime()
	{
		Calendar cal = Calendar.getInstance();
		
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		
	    return hr<7;
	}
	
	private MimeMessage createMimeMsg(String body,String subject,List<String> bccList) throws MessagingException
	{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setText(body, true);
		
		helper.setSubject(subject);
		
		String[] bcc = new String[bccList.size()];
		bcc = bccList.toArray(bcc);
		
		helper.setBcc(bcc);
		
		return mimeMessage;
	}
}
