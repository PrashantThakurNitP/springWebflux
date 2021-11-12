package com.reactive.webflux.config;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.webflux.dto.InputFailedValidationResponse;
import com.reactive.webflux.exception.InputValidationException;

import reactor.core.publisher.Mono;

@Configuration
public class RouterConfig {
	//we are using functional endpoint
	@Autowired
	private RequestHandler requestHandler;
	
	//here there is nested routing and when request comes to router/<anything>
	//then it goes to next bean
	@Bean
	public RouterFunction<ServerResponse> highLevelRouter(){
		return RouterFunctions.route()
				.path("router", this::serverResponseRouterFunction)
				.build();
	}
	//router/square/{input}
//	@Bean
	private RouterFunction<ServerResponse> serverResponseRouterFunction(){
		return RouterFunctions.route()
				.GET("square/{input}",requestHandler::squareHandler)
				.GET("table/{input}",requestHandler::tableHandler)
				.GET("table/{input}/stream",requestHandler::tableStreamHandler)
				.POST("multiply",requestHandler::multiplyHandler)
				.GET("square/{input}/validation",requestHandler::squareHandlerWithValidation)
				
				.onError(InputValidationException.class, exceptionHandler())
				//if thrown error is of type InputValidationException then execute exceptionHandler
				.build();
	}
	private BiFunction <Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler(){
		return (err,req)->{
			InputValidationException ex =(InputValidationException)err;
			InputFailedValidationResponse response = new InputFailedValidationResponse();
			response.setInput(ex.getInput());
			response.setMessage(ex.getMessage());
			response.setErrorCode(ex.getErrorcode());
			return ServerResponse.badRequest().bodyValue(response);
		};
	}

}
