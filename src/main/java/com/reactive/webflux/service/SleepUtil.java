package com.reactive.webflux.service;

public class SleepUtil {
	public static void sleepSeconds(int second) 
	{
		try {
			Thread.sleep(second*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
