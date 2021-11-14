package com.reactive.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

	@Bean//exponse spring bean
	public WebClient webClient() {//expose webclient
		//we will send response to service built earlier
		return WebClient.builder()
				.baseUrl("http://localhost:8080")
				//.filter((clientRequest,exchangeFunction)->sessionToken(clientRequest,exchangeFunction))
				//above commented line is same as written below by method reference
				.filter(this::sessionToken)
			//	.defaultHeaders(h->h.setBasicAuth("usename","password"))
				.build();
	}
	private Mono<ClientResponse>sessionToken(ClientRequest request, ExchangeFunction ex){
		System.out.println("generating session token");
		ClientRequest clientRequest = ClientRequest.from(request).headers(h->h.setBearerAuth("some lengthy - jwt web token")).build();
		return ex.exchange(clientRequest);
	}
	
}
