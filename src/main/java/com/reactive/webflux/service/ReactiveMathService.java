package com.reactive.webflux.service;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.reactive.webflux.dto.MultiplyRequestDto;
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
				.doOnNext((i)->System.out.println("checking"))
				.doOnNext((i)->System.out.println("thread name "+Thread.currentThread().getName()+" "+i))// it is blocking sleep. we need non blocking sleep
				//.doOnNext(i->SleepUtil.sleepSeconds(1))
					.delayElements(Duration.ofSeconds(1)) //it is non blocking sleep
				.doOnNext(i->System.out.println("Reactive math service processing:"+i))
				.map(i->new Response(i*input));
		
	}
	
	
	public Mono<Response> multiply( Mono<MultiplyRequestDto> dtoMono) {
		return dtoMono
				.map(dto->dto.getFirst()*dto.getSecond())
				.map(Response::new);
		
	}


	
	
	
	
}
