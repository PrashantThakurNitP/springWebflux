package com.reactive.webflux.service;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.reactive.webflux.dto.Response;

@Service
public class MathService {
	public Response findSquare(int input) {
		return new Response(input*input);
	}
	public List<Response> multiplicationTable(int input){
		return IntStream.rangeClosed(1,10)
				.peek(i->SleepUtil.sleepSeconds(1))//return element at top of container but donot delete it
				.peek(i->System.out.println("processing element in math service "+i))
				.mapToObj(i-> new Response(i*input))
				.collect(Collectors.toList());//colect convert stream to some data structure here list from collectors
				
	}

}
