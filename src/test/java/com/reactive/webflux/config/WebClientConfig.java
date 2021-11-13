package com.reactive.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean//exponse spring bean
	public WebClient webClient() {//expose webclient
		//we will send response to service built earlier
		return WebClient.builder()
				.baseUrl("http://localhost:8080")
				.build();
	}
	
}
