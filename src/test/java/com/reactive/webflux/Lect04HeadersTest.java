package com.reactive.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.webflux.dto.MultiplyRequestDto;
import com.reactive.webflux.dto.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lect04HeadersTest extends BaseTest {
	@Autowired
	private WebClient webClient;
	
	@Test
	public void headersTest() {
		//we will send headers information
		Mono<Response> responseMono = this.webClient
		.post()
		.uri("reactive-math/multiply")
		.bodyValue(buildRequestDto(5,2))//dody is of type objevt it is not of publisher type
		.headers(h->h.set("somekey","someValue"))
		.retrieve()//send request and give me response and give
		.bodyToMono(Response.class)//convert to Response class
		.doOnNext(System.out::println);
		
		StepVerifier.create(responseMono)
		.expectNextCount(1)//verify that one output will be there
		.verifyComplete();
		
		
	}
	private MultiplyRequestDto buildRequestDto(int a , int b) {
		MultiplyRequestDto dto = new MultiplyRequestDto();
		dto.setFirst(a);
		dto.setSecond(b);
		return dto;
	}

}
