package com.reactive.webflux.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.reactive.webflux.dto.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {
	
	public Mono<Response> findSquare(int input){
		return Mono.fromSupplier(()->input*input)
				//we have to build pipeline and all calculation have to be done in reactive way
				.map(Response::new);
				//.map(v-> new Response(v))
	}
	
	public Flux<Response> multiplicationTable(int input) {
		return Flux.range(1, 10)
			//	.doOnNext(i->SleepUtil.sleepSeconds(input)) it is blocking sleep. we need non blocking sleep
				.delayElements(Duration.ofSeconds(1)) //it is non blocking sleep
				.doOnNext(i->System.out.println("Reactive math service processing :"+ i))
				.map(i->new Response(i*input));
		
	}

	
	
	
	
}
