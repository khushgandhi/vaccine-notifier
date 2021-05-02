package com.vaccine.notifier.vaccinenotifier.controller;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vaccine.notifier.vaccinenotifier.dto.Center;
import com.vaccine.notifier.vaccinenotifier.model.Subscriber;
import com.vaccine.notifier.vaccinenotifier.schedular.VaccineNotifierSchedular;
import com.vaccine.notifier.vaccinenotifier.service.SubscriberService;
import com.vaccine.notifier.vaccinenotifier.service.VaccineNotifierService;

@RestController
@RequestMapping(value="/vaccine-notifier")
public class VaccineNotifierController {

	@Autowired
	VaccineNotifierService vaccineNotifierService;
	@Autowired
	SubscriberService subscriberService;
	
	@Autowired
	VaccineNotifierSchedular vaccineNotifierSchedular;
	
	@RequestMapping(value="/getNextSlot",method={RequestMethod.OPTIONS,RequestMethod.GET})
	public List<Center> getNextAvailableSlots(@RequestParam(name = "district_id") Long districtId,@RequestParam Integer minAge) throws URISyntaxException
	{
		return this.vaccineNotifierService.getNextAvailableSlots(districtId, minAge);
	}
	
	@RequestMapping(value="/cron",method={RequestMethod.OPTIONS,RequestMethod.GET})
	public void f() throws ParseException
	{
		 vaccineNotifierSchedular.findAvailableCenters();
	}
	
	@RequestMapping(value="/addSubscriber",method={RequestMethod.OPTIONS,RequestMethod.POST})
	public void addSubscriber(@RequestBody Subscriber subscriber) throws Exception
	{
		subscriberService.addSubscriber(subscriber);	
	}
	
	@RequestMapping(value="/deleteSubscriber",method={RequestMethod.OPTIONS,RequestMethod.DELETE})
	public void deleteSubscriber(@RequestBody Subscriber subscriber) throws Exception
	{
		subscriberService.deleteSubscriber(subscriber);
	}
	
	
}
