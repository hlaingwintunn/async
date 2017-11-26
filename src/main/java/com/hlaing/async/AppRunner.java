package com.hlaing.async;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);
	
	private final GitHubLookupService lookUpService;
	
	public AppRunner(GitHubLookupService lookUpService) {
		this.lookUpService = lookUpService;
	}

	@Override
	public void run(String... arg0) throws Exception {
        // Start the clock 
		
		final long start = System.currentTimeMillis();
		
		//kick of Multiple asynchrous lookups
        CompletableFuture<User> page1 = lookUpService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = lookUpService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = lookUpService.findUser("Spring-Projects");
        
        //wait until they all are done.
        CompletableFuture.allOf(page1, page2, page3).join();
        
     // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
	}

}
