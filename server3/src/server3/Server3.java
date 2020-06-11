/*
 * Server3.java
 * Haoran Ren & Dayuan Tan
 * CMSC 621 Fall 2018
 * Project - Distributed System
 * 
 * This file defines the web services provided by server3.
 * Services are simple terminal prints and random delays to simulate processing time.
 * Server3 provides service2 and service3.
 * 
 */

package server3;

import java.util.concurrent.TimeUnit;

public class Server3 {
	
	public final static int DELAY_MAX = 5;
	
	public int service2(String client) {
		int delay = -1;
		delay = (int) (Math.random() * DELAY_MAX + 1);
		System.out.println("[ REQUEST ]\tClient " + client + " requests service2 on server3. Processing time: " + delay + " seconds.");
		try {
			TimeUnit.SECONDS.sleep(delay);
		} catch (InterruptedException e) {
			System.err.println("[ EXCEPTION ]\tTimeUnit.SECONDS.sleep() throws an exception.");
		}
		return delay;
	}
	
	public int service3(String client) {
		int delay = -1;
		delay = (int) (Math.random() * DELAY_MAX + 1);
		System.out.println("[ REQUEST ]\tClient " + client + " requests service3 on server3. Processing time: " + delay + " seconds.");
		try {
			TimeUnit.SECONDS.sleep(delay);
		} catch (InterruptedException e) {
			System.err.println("[ EXCEPTION ]\tTimeUnit.SECONDS.sleep() throws an exception.");
		}
		return delay;
	}
}
