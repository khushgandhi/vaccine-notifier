package com.vaccine.notifier.vaccinenotifier.utill;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vaccine.notifier.vaccinenotifier.dto.Center;

@Service
public class CacheRepository {

	private final RedisTemplate<String,Center> redisTemplate;
	
	private ListOperations<String, Center> ListOps;
	
	@Value("${ttl}")
    private Long ttl;	
	
	public CacheRepository(RedisTemplate<String,Center> redisTemplate)
	{
		this.redisTemplate = redisTemplate;
		
		
		ListOps = redisTemplate.opsForList();
	}
	
	public void save(String key,List<Center> centers)
	{
		if(!CollectionUtils.isEmpty(centers))
		{
			System.out.println("Added Centers for key -: "+key);
			this.ListOps.rightPushAll(key, centers);
			
			this.redisTemplate.expire(key, Duration.ofMinutes(ttl));
		}
		
	}
	
	public List<Center> find(String key)
	{
		return  this.ListOps.range(key, 0, -1);
	}
	
	public boolean contains(String key)
	{
		if(this.ListOps.size(key)!=0)
		{
			System.out.println("Cache hit for "+key +" "+ this.ListOps.size(key));
			return true;
		}
		
		return false;
	}
}
