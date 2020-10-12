package es.urjc.code.policy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.codec.ErrorDecoder;

@Configuration
public class DefaultDecoderConfiguration {
	
	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
	
	@Bean
	public ErrorDecoder policyFeignErrorDecoder() {
		return new DefaultErrorDecoder();
	}
}