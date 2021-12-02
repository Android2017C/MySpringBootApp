package com.zensar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommendLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("inside MyCommendLineRunner ....");
		for(String arg : args) {
			System.out.println(" MyCommendLineRunner out put : "+args);
		}
	}

}
