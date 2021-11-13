package com.reactive.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.webflux.dto.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends BaseTest{

	@Autowired//create this as a bean
	private WebClient webClient;
	//now we will send request and get response
	
	@Test
	public void blockTest() {
	Response response=	this.webClient//send get request
		.get()
		.uri("reactive-math/square/{number}", 5)//it will take 5 and subsitute in number
		.retrieve() //send request and try to get response
		.bodyToMono(Response.class) //we are expecting body and it will be single response
		//Response is reponse we get we will be getting Mono <response>, 
		.block();// block is not god but it is test class
	
	System.out.println(response);
	}
	
	
	@Test
	public void stepVerifierTest() {//w e need reactire test depndency
	Mono<Response> responseMono=	this.webClient//send get request
		.get()
		.uri("reactive-math/square/{number}", 5)//it will take 5 and subsitute in number
		.retrieve() //send request and try to get response
		.bodyToMono(Response.class) ;//we are expecting body and it will be single response
		//Response is reponse we get we will be getting Mono <response>, 
	
	
	StepVerifier.create(responseMono)
		.expectNextMatches(r-> r.getOutput() == 26)
		.verifyComplete();
	
	/*
	 * Step verifier
	 * 
	 * It comes from reactor test dependency It is library to test publisher type
	 * Step verifier comes from reactor test library We pass publisher type in Then
	 * we tell what we expect out of publisher StepVerifier.create()
	 * 
	 * Next → what we expect out of publisher Verify → after we StepVerifierOptions
	 * → other parameter We need step verifier because webclient is non-blocking and
	 * async so test will exit immediately ,
	 */

	}
}
