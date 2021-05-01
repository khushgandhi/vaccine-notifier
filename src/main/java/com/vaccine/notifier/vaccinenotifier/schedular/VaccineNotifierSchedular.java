package com.vaccine.notifier.vaccinenotifier.schedular;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vaccine.notifier.vaccinenotifier.dto.Center;
import com.vaccine.notifier.vaccinenotifier.dto.District;
import com.vaccine.notifier.vaccinenotifier.dto.State;
import com.vaccine.notifier.vaccinenotifier.service.VaccineNotifierService;

class StateResponse
{
	private List<State> states;

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}
	
}

class DistrictResponse
{
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
	VaccineNotifierService VaccineNotifierService;
	
	@Value("${cowin.states.endpoint}")
	private String cowinStatesEndpoint;
	
	@Value("${cowin.district.endpoint}")
	private String cowinDistrictEndpoint;

	@Value("${cowin.available.ageslots}")
	private List<Integer> cowinAvailableAgeSlots;
	
	public void findAvailableCenters()
	{
		
		List<State> states = getStates();
		
		if(!CollectionUtils.isEmpty(states))
		{
			states.stream().forEach(state->{
				List<District> districts = getDistricts(state.getStateId());
				if(!CollectionUtils.isEmpty(districts))
				{
					districts.stream().forEach(district->{
		
							cowinAvailableAgeSlots.stream().forEach(ageSlot->{
								try {
									List<Center> centers = VaccineNotifierService.getNextAvailableSlots(district.getDistrictId(), ageSlot);
									System.out.println(district.getDistrictId()+" "+ageSlot+" "+centers.size());
								} catch (URISyntaxException e) {
									e.printStackTrace();
								}
							});
							
							
					});
				}
				
			});
			
		}
		
	}
	
	
	private List<District> getDistricts(Long stateId)
	{
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(cowinDistrictEndpoint).path("/"+stateId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");
		
		HttpEntity<Object> request = new HttpEntity<>(headers);

		String uri = builder.toUriString();
		
		System.out.println("Calling cowin api -: "+uri);
		
		try {
			ResponseEntity<DistrictResponse> response = restTemplate.exchange(
			        builder.toUriString(), 
			        HttpMethod.GET, 
			        request, 
			        DistrictResponse.class);
			
			if (response.getBody() != null  && (response.getStatusCode().is2xxSuccessful()) && response.getBody().getDistricts()!=null)
			{
				return response.getBody().getDistricts();
			}
		}
		catch(Exception ex)
		{
			System.out.println("Can't fetch available  districts");
			System.out.println(ex);
		}
		
		
	  return new ArrayList<District>();	
	
	}
	

	private List<State> getStates()
	{
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(cowinStatesEndpoint);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");
		
		HttpEntity<Object> request = new HttpEntity<>(headers);

		String uri = builder.toUriString();
		
		System.out.println("Calling cowin api -: "+uri);
		
		try {
			ResponseEntity<StateResponse> response = restTemplate.exchange(
			        builder.toUriString(), 
			        HttpMethod.GET, 
			        request, 
			        StateResponse.class);
			
			if (response.getBody() != null  && (response.getStatusCode().is2xxSuccessful()) && response.getBody().getStates()!=null)
			{
				return response.getBody().getStates();
			}
		}
		catch(Exception ex)
		{
			System.out.println("Can't fetch available  districts");
			System.out.println(ex);
		}
		
		return new ArrayList<State>();
	}
}

