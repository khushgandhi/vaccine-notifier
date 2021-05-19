package com.vaccine.notifier.vaccinenotifier.service;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.vaccine.notifier.vaccinenotifier.dto.Session;
import com.vaccine.notifier.vaccinenotifier.utill.CacheRepository;

class DistrictResponse
{
	private List<Center> centers;

	public List<Center> getCenters() {
		return centers;
	}

	public void setCenters(List<Center> centers) {
		this.centers = centers;
	}
	
}
@Service
public class VaccineNotifierService {

	@Value("${cowin.centerByDistrict.endpoint}")
	private String cowinCenterByDistrictUrl;

	@Autowired
	CacheRepository cacheRepo;
	

	public List<Center> getNextAvailableSlots(Long districtId,Integer minAge,boolean isCron) throws URISyntaxException
	{
		String ageKey = districtId.toString()+"_"+minAge;
		if( this.cacheRepo.contains(ageKey))
		{
			return this.cacheRepo.find(ageKey);
		}
		
		Map<String,Center> centersMap = new LinkedHashMap<>();
		
		for(int i=0;i<2;i++)
		{
			String date = getDateWithOffset(i*7);
			
			String dateKey = districtId.toString()+"_"+date;
			List<Center> allCenters = new ArrayList<>();
			
			if( this.cacheRepo.contains(dateKey))
			{
				allCenters = this.cacheRepo.find(dateKey);
			}
			else
			{
				allCenters = this.getCenters(districtId,date,isCron);
				this.cacheRepo.save(dateKey, allCenters);
			}
			
			allCenters.stream().forEach(center->{
				
				List<Session> filterdSessions = center.getSessions().stream().filter(session->
				{
					if(session.getAvailableCapacity()>0 && session.getMinAgeLimit() == minAge)
					{
						return true;
					}
					return false;
				}).collect(Collectors.toList());
				
				if(!CollectionUtils.isEmpty(filterdSessions))
				{
					insertSession(centersMap,center,filterdSessions);
				}
			});
		}
		
		if(centersMap.isEmpty())
		{
			return new ArrayList<>();
		}
		
		List<Center> filterdCenters = new ArrayList<>(centersMap.values());
		this.cacheRepo.save(ageKey, filterdCenters);
		return filterdCenters;
	}
	
	private void insertSession(Map<String, Center> centersMap,Center center, List<Session> filteredSessions) {
	
		if(centersMap.containsKey(center.getCenterId().toString()))
		{
			centersMap.get(center.getCenterId().toString()).getSessions().addAll(filteredSessions);
		}
		else
		{
			center.setSessions(filteredSessions);
			centersMap.put(center.getCenterId().toString(), center);
		}
		
	}

	
	private List<Center> getCenters(Long districtId,String date,boolean isCron) throws URISyntaxException
	{
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(cowinCenterByDistrictUrl)
				.queryParam("district_id", districtId)
				.queryParam("date",date);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");
		headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.51");
		headers.set("origin", "https://www.cowin.gov.in");
		
		HttpEntity<Object> request = new HttpEntity<>(headers);

		String uri = builder.toUriString();
		
		if(isCron)
		{
			try {
				System.out.println("calm down for 2 secs....");
				Thread.sleep(2000);
				System.out.println("Start for next iteration..");
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
		
		System.out.println("Calling cowin api -: "+uri);
		
		try {
			ResponseEntity<DistrictResponse> response = restTemplate.exchange(
			        builder.toUriString(), 
			        HttpMethod.GET, 
			        request, 
			        DistrictResponse.class);
			
			if (response.getBody() != null  && (response.getStatusCode().is2xxSuccessful()) && response.getBody().getCenters()!=null)
			{
				return response.getBody().getCenters();
			}
		}
		catch(Exception ex)
		{
			System.out.println("Can't fetch available centers for districtId "+districtId+" On date "+date);
			System.out.println(ex);
		}
		
		
		
	  return new ArrayList<Center>();	
	}
	
	private String getDateWithOffset(int offset)
	{
		Calendar cal= Calendar.getInstance();
		cal.add(Calendar.DATE, offset);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		return dateFormat.format(cal.getTime());
	}
}
