package com.reactive.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.reactive.webflux.dto.InputFailedValidationResponse;
import com.reactive.webflux.dto.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest{

	//exchange is retrieve + additional information like http status code
	@Autowired 
	private WebClient webclient;
	
	@Test
	public void badRequestTest() {
		Mono<Object> responseMono = this.webclient
				.get()
				.uri("recative-math/square/{number}/throw",5)
				.exchangeToMono(this::exchange)
				
				
				.doOnNext(System.out::println)
				.doOnError(err->System.out.println(err.getMessage()));
		StepVerifier.create(responseMono)
		.expectNextCount(1)
		.verifyComplete();
	}
	
	private Mono<Object> exchange(ClientResponse cr){
		if(cr.rawStatusCode()==400) {
			return cr.bodyToMono(InputFailedValidationResponse.class);
		}
		else {
			return cr.bodyToMono(Response.class);
		}
			
	}
}
