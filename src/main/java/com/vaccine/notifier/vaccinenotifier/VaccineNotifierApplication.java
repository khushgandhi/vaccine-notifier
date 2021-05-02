package com.vaccine.notifier.vaccinenotifier;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.vaccine.notifier.vaccinenotifier.dto.Center;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class VaccineNotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccineNotifierApplication.class, args);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
	    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
	    jedisConnectionFactory.getPoolConfig().setMaxIdle(30);
	    jedisConnectionFactory.getPoolConfig().setMinIdle(10);
	    
	    return jedisConnectionFactory;
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
