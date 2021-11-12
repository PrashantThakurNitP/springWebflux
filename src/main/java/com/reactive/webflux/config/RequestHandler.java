package com.reactive.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.webflux.dto.InputFailedValidationResponse;
import com.reactive.webflux.dto.MultiplyRequestDto;
import com.reactive.webflux.dto.Response;
import com.reactive.webflux.exception.InputValidationException;
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
		
		//postman till now donot support steaming response

	}
	
	
	public Mono<ServerResponse>tableStreamHandler(ServerRequest serverRequest){
		int input=Integer.parseInt(  serverRequest.pathVariable("input"));
		Flux<Response> responseFlux = this.mathService.multiplicationTable(input);// we are building pipeline, we are not subscribing
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(responseFlux,Response.class);//first argument is publishert type and second is type of class
		//server response is object which contain flux response inside
		
		//postman till now donot support steaming response

	}
	
	public Mono<ServerResponse>multiplyHandler(ServerRequest serverRequest){
		Mono<MultiplyRequestDto> requestDtoMono = serverRequest.bodyToMono(MultiplyRequestDto.class);//publisher type
		Mono<Response> responseMono = this.mathService.multiply(requestDtoMono );
		
		return ServerResponse.ok()
				
				.body( responseMono,Response.class);//first argument is publishert type and second is type of class
		//server response is object which contain flux response inside
		
		//postman till now donot support steaming response

	}
	

	
	public Mono<ServerResponse>squareHandlerWithValidation(ServerRequest serverRequest){
		int input=Integer.parseInt(  serverRequest.pathVariable("input"));
		if(input<10 || input >20)
		{
			return Mono.error(new InputValidationException(input));
			//we will handle this error in RouterConfig
		//	InputFailedValidationResponse response = new InputFailedValidationResponse();
		//	return ServerResponse.badRequest().bodyValue(response);
		}
		Mono<Response> responseMono = this.mathService.findSquare(input);// we are building pipeline, we are not subscribing
		return ServerResponse.ok().body(responseMono,Response.class);//first argument is publishert type and second is type of class
		
//		Response response= new Response();
//		return ServerResponse.ok().bodyValue(response);
		//when response then use bodyvalue
	}
	
	
	
	
	
	

}



