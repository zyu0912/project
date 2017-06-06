package org.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainStart {
	private static final Logger logger = LoggerFactory.getLogger(MainStart.class);
	
	public static void main(String[] args) {
		SpringApplication.run(MainStart.class, args);
		logger.info("Tic-Tac-Toe game service start...");
	}
}
