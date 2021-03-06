package com.vaccine.notifier.vaccinenotifier;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.vaccine.notifier.vaccinenotifier.dto.Center;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableEncryptableProperties
public class VaccineNotifierApplication {

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${secret}")
	private String secret;

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(VaccineNotifierApplication.class, args);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, port);

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
		jedisConnectionFactory.getPoolConfig().setMaxIdle(30);
		jedisConnectionFactory.getPoolConfig().setMinIdle(10);

		System.out.println("-------->" + jedisConnectionFactory.getHostName());

		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Center> redisTemplate() {
		RedisTemplate<String, Center> template = new RedisTemplate<>();
		template.setEnableTransactionSupport(true);
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	@Bean(name = "jasyptStringEncryptor")
	public StringEncryptor getPasswordEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

		SimpleStringPBEConfig config = new SimpleStringPBEConfig();

		config.setPassword(secret);
		System.out.println("----------->"+secret);
		config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
	    config.setKeyObtentionIterations("1000");
	    config.setPoolSize("1");
	    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
	    config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
	    config.setStringOutputType("base64");
		encryptor.setConfig(config);

		return encryptor;
	}

	@PostConstruct
	public void init() {
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}
}
