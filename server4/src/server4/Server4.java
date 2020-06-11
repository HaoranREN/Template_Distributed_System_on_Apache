/*
 * Server4.java
 * Haoran Ren & Dayuan Tan
 * CMSC 621 Fall 2018
 * Project - Distributed System
 * 
 * This file defines the web services provided by server4.
 * Services are simple terminal prints and random delays to simulate processing time.
 * Server4 provides service1, service2, and service3.
 * 
 */

package server4;

import java.util.concurrent.TimeUnit;

public class Server4 {
	
	public final static int DELAY_MAX = 5;
	
	public int service1(String client) {
		int delay = -1;
		delay = (int) (Math.random() * DELAY_MAX + 1);
		System.out.println("[ REQUEST ]\tClient " + client + " requests service1 on server4. Processing time: " + delay + " seconds.");
		try {
			TimeUnit.SECONDS.sleep(delay);
		} catch (InterruptedException e) {
			System.err.println("[ EXCEPTION ]\tTimeUnit.SECONDS.sleep() throws an exception.");
		}
		return delay;
	}
	
	public int service2(String client) {
		int delay = -1;
		delay = (int) (Math.random() * DELAY_MAX + 1);
		System.out.println("[ REQUEST ]\tClient " + client + " requests service2 on server4. Processing time: " + delay + " seconds.");
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
		System.out.println("[ REQUEST ]\tClient " + client + " requests service3 on server4. Processing time: " + delay + " seconds.");
		try {
			TimeUnit.SECONDS.sleep(delay);
		} catch (InterruptedException e) {
			System.err.println("[ EXCEPTION ]\tTimeUnit.SECONDS.sleep() throws an exception.");
		}
		return delay;
	}
}
