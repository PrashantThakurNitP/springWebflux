package com.reactive.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.webflux.dto.Response;
import com.reactive.webflux.exception.InputValidationException;
import com.reactive.webflux.service.ReactiveMathService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {
	
	@Autowired
	ReactiveMathService mathService;
	
	@GetMapping("square/{input}/throw")
	public Mono<Response> findSquare(@PathVariable int input) {
		if(input<10 || input>20 )
			throw new InputValidationException(input);
		return this.mathService.findSquare(input);
	}

}
