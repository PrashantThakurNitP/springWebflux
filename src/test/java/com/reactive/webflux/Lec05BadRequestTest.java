package com.reactive.webflux;

import org.junit.jupiter.api.Test;

import com.reactive.webflux.dto.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec05BadRequestTest extends BaseTest{
	
	@Autowired
	private WebClient webClient;
	@Test
	public void badRequest() {
		
		Mono<Response>responseMono=	this.webClient//send get request
				.get()
				.uri("reactive-math/square/{number}/throw", 5)//it will take 5 and subsitute in number
				.retrieve() //send request and try to get response
				.bodyToMono(Response.class) //we are expecting body and it will be single response
				.doOnNext(System.out::println)
				//Response is reponse we get we will be getting Mono <response>, 
				.doOnError(err->System.out.println(err.getMessage()));
			
			
			StepVerifier.create(responseMono)
				.verifyError(WebClientResponseException.class);//verify that bad request will occur
		
	}

	
}
