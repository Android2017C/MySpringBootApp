package com.zensar;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class MyApplicationRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("inside MyApplicationRunner ......"+args.getSourceArgs());
		for(String arg : args.getSourceArgs()) {
			System.out.println(args);
		}
	}

}
