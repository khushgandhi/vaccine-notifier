package com.vaccine.notifier.vaccinenotifier.schedular;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
	
	private static String SLOTS_AVAILABLE_SUB = "Hurry vaccination slots are available near you!!";

	@Scheduled(cron = "${cron.expression}")
	public void findAvailableCenters() throws ParseException {
		if(isNightTime())
		{
			System.out.println("Cant't send email at night time -:"+ new Date());
			return;
		}
		
		Date startDate = this.getMinDate();
		Date endDate = this.getMaxDate();
		
		Set<DistinctDistrictAge> districtAgePairs = subscriberRepository.findDistinctDistricts(startDate,endDate);

		if (!CollectionUtils.isEmpty(districtAgePairs)) {
			districtAgePairs.forEach(districAgePair -> {
				Long districtId = districAgePair.getDistrictId();
				Integer minAge = districAgePair.getMinAge();

				List<Center> centers = new ArrayList<>();
				
				try {
					centers = VaccineNotifierService.getNextAvailableSlots(districtId, minAge);
				} catch (URISyntaxException e) {
					System.out.println(
							"getNextAvailableSlots failed for district-:" + districtId + ",ageSlot-:" + minAge);

					e.printStackTrace();
				}
				
				if (!CollectionUtils.isEmpty(centers)) {
					int size = centers.size();
					System.out.println(districtId + " " + minAge + " " + size);
					Set<Subscriber> subscribers = subscriberRepository.findValidSubscribers(districtId, minAge, startDate, endDate);
					if (!CollectionUtils.isEmpty(subscribers)) {
						subscribers.forEach(subscriber -> {
                             try {
								this.emailService.sentMailWhenCenterFound(subscriber.getEmailId(),SLOTS_AVAILABLE_SUB,MailTemplate.getSlotsAvailableMsg(size, getSlotAvailableUrl(subscriber)),subscriber);
							} catch (Exception ex) {
								System.out.println("Exception while sending mail to "+subscriber.getEmailId());
								System.out.println(ex);
							}

                            subscriber.setLastNotifiedAt(new Date());
   							subscriberRepository.save(subscriber);
							
						});
						
					}
				}

			});
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
	private String getSlotAvailableUrl(Subscriber sub)
	{
	
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(vaccineNotifierUrl)
				.queryParam("districtId", sub.getDistrictId())
				.queryParam("stateId",sub.getStateId())
				.queryParam("minAge", sub.getMinAge());
		
         
		return builder.toUriString();
	}
	
	private boolean isNightTime()
	{
		Calendar cal = Calendar.getInstance();
		
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		
	    return hr<7;
	}
}
