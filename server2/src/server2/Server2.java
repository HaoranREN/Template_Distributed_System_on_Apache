/*
 * Server2.java
 * Haoran Ren & Dayuan Tan
 * CMSC 621 Fall 2018
 * Project - Distributed System
 * 
 * This file defines the web services provided by server2.
 * Services are simple terminal prints and random delays to simulate processing time.
 * Server2 provides service1 and service3.
 * 
 */

package server2;

import java.util.concurrent.TimeUnit;

public class Server2 {
	
	public final static int DELAY_MAX = 5;
	
	public int service1(String client) {
		int delay = -1;
		delay = (int) (Math.random() * DELAY_MAX + 1);
		System.out.println("[ REQUEST ]\tClient " + client + " requests service1 on server2. Processing time: " + delay + " seconds.");
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
		System.out.println("[ REQUEST ]\tClient " + client + " requests service3 on server2. Processing time: " + delay + " seconds.");
		try {
			TimeUnit.SECONDS.sleep(delay);
		} catch (InterruptedException e) {
			System.err.println("[ EXCEPTION ]\tTimeUnit.SECONDS.sleep() throws an exception.");
		}
		return delay;
	}
}