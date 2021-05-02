package com.vaccine.notifier.vaccinenotifier;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.vaccine.notifier.vaccinenotifier.dto.Center;

@SpringBootApplication
@EnableCaching
public class VaccineNotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccineNotifierApplication.class, args);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
	    return new JedisConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Center> redisTemplate() {
	    RedisTemplate<String, Center> template = new RedisTemplate<>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    return template;
	}
	
	@PostConstruct
	  public void init(){
	    // Setting Spring Boot SetTimeZone
	    TimeZone.setDefault(TimeZone.getTimeZone("IST"));  
	  }
}
