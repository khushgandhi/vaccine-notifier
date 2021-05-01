package com.vaccine.notifier.vaccinenotifier.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vaccine.notifier.vaccinenotifier.dto.Center;
import com.vaccine.notifier.vaccinenotifier.service.VaccineNotifierService;

@RestController
@RequestMapping(value="/vaccine-notifier")
public class VaccineNotifierController {

	@Autowired
	VaccineNotifierService vaccineNotifierService;
	
	@RequestMapping(value="/getNextSlot",method={RequestMethod.OPTIONS,RequestMethod.GET})
	public List<Center> getNextAvailableSlots(@RequestParam(name = "district_id") Integer districtId,@RequestParam Integer minAge) throws URISyntaxException
	{
		return this.vaccineNotifierService.getNextAvailableSlots(districtId, minAge);
	}
}
