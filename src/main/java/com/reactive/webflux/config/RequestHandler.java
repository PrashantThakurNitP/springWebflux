package com.reactive.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.webflux.dto.Response;
import com.reactive.webflux.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {
	
	@Autowired
	private ReactiveMathService mathService ;
	
	//we take serverRequest and return Mono of server respone as Router Config expect it(second argument of .GET is of this type)
	public Mono<ServerResponse>squareHandler(ServerRequest serverRequest){
		int input=Integer.parseInt(  serverRequest.pathVariable("input"));
		Mono<Response> responseMono = this.mathService.findSquare(input);// we are building pipeline, we are not subscribing
		return ServerResponse.ok().body(responseMono,Response.class);//first argument is publishert type and second is type of class
		
//		Response response= new Response();
//		return ServerResponse.ok().bodyValue(response);
		//when response then use bodyvalue
	}
	
	public Mono<ServerResponse>tableHandler(ServerRequest serverRequest){
		int input=Integer.parseInt(  serverRequest.pathVariable("input"));
		Flux<Response> responseFlux = this.mathService.multiplicationTable(input);// we are building pipeline, we are not subscribing
		return ServerResponse.ok().body(responseFlux,Response.class);//first argument is publishert type and second is type of class
		//server response is object which contain flux response inside
		
		//postamn till now donot support steaming response

	}
	
	
	

}



