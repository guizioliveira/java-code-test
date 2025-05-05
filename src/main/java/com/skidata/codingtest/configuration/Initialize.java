package com.skidata.codingtest.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initialize {

	@Autowired
	PersonInitializer persinit;
	@Autowired
	UserInitializer usrinit;

	@PostConstruct
	void init() {
		persinit.init();
		usrinit.init();
	}

}
