package com.vaccine.notifier.vaccinenotifier.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vaccine.notifier.vaccinenotifier.dto.DistinctDistrictAge;
import com.vaccine.notifier.vaccinenotifier.model.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, String>{

	@Query(value="select s.districtId AS districtId,s.minAge AS minAge from Subscriber s where (s.lastNotifiedAt < ?1 or s.lastNotifiedAt is null) and s.isActive=true GROUP BY s.districtId,s.minAge")
	public Set<DistinctDistrictAge> findDistinctDistricts(Date today);
	
	@Query(value="select s from Subscriber s where s.districtId = ?1 and s.minAge = ?2 and (s.lastNotifiedAt < ?3 or s.lastNotifiedAt is null) and s.isActive=true")
	public Set<Subscriber> findValidSubscribers(Long districtId,Integer minAge,Date today);
}
