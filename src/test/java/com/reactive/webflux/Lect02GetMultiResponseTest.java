package com.reactive.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.webflux.dto.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lect02GetMultiResponseTest extends BaseTest {

	@Autowired
	private WebClient WebClient;
	
	@Test
	public void fluxTest() {
		
		Flux<Response> responseFlux=	this.WebClient//send get request
				.get()
				.uri("reactive-math/table/{number}/stream", 5)//it will take 5 and subsitute in number
				.retrieve() //send request and try to get response
				.bodyToFlux(Response.class) //we are expecting body and it will be single response
				.doOnNext(System.out::println);
				//Response is reponse we get we will be getting Mono <response>, 
			
			
			StepVerifier.create(responseFlux)
				.expectNextCount(10)//this expect 10 item
				.verifyComplete();
			
		
	}
}
