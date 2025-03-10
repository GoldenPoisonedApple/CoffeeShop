package com.mamezou.shop.sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {
	private static Logger logger = LogManager.getLogger(LogTest.class);

	public void act () {
		logger.error("Error message");
		logger.warn("Warn message");
		logger.info("Info message");
		logger.debug("Debug message");
		logger.trace("Trace message");

		// This is a simple log message
		System.out.println("Hello, World!");
	}


	public static void main(String[] args) {
		new LogTest().act();
	}
}
