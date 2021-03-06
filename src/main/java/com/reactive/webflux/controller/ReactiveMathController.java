package com.reactive.webflux.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.webflux.dto.MultiplyRequestDto;
import com.reactive.webflux.dto.Response;
import com.reactive.webflux.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {
	@Autowired
	ReactiveMathService mathService;
	
	@GetMapping("square/{input}")
	public Mono<Response> findSquare(@PathVariable int input) {
		return this.mathService.findSquare(input);
	}
	@GetMapping("table/{input}")
	public Flux<Response> multiplicationTable(@PathVariable int input) {
		return this.mathService.multiplicationTable(input);
		
	}
	//backend service act as publisher and frontend (or some other service) will be subscribing to it
	//so in our code we will not be subscribing but we will be writing publisher of some type ie Mono or flux
	// we are returning lazily in async way we should not use .block()
	
	
	//instead of sending whole response we can send streaming response
	
	@GetMapping(value="table/{input}/stream", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> multiplicationTableStream(@PathVariable int input) {
		return this.mathService.multiplicationTable(input);
		
		//now subscriber get the the value as and when backend service does something
		
	}
	//here in case of post request we are reading data in reactive way as data can be too large
	//hence request body is mono
	//request header not working
	@PostMapping("multiply")
	public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
									@RequestHeader Map<String,String> headers	) {
		System.out.println(headers);
		return this.mathService.multiply(requestDtoMono);
		
	}

}
