/*
 * CentralServer.java
 * Haoran Ren & Dayuan Tan
 * CMSC 621 Fall 2018
 * Project - Distributed System
 * 
 * This file defines all the web services provided by the central server.
 * The central server provides services discovery and load balancing to the system.
 * The services registry is stored in a ServicesList object.
 * All the servers report their services to the central server when the service is online.
 * The load balancer return the url of the mininum-loaded instance of a certain service.
 * 
 */

package centralServer;

public class CentralServer {

	public ServicesList services = new ServicesList();
	
	public boolean registerService(String serviceName, String serverName, String url) {
		System.out.println("[ REGISTRY ]\t" + serverName + " registers " + serviceName + ".");
		return services.registerService(serviceName, serverName, url);
	}
	
	public boolean removeService(String serviceName, String serverName) {
		System.out.println("[ REGISTRY ]\t" + serverName + " removes " + serviceName + ".");
		return services.removeService(serviceName, serverName);
	}
	
	public String requestService(String serviceName, String client) {
		System.out.println("[ REQUEST ]\tClient " + client + " requests " + serviceName + ".");
		return services.requestService(serviceName, client);
	}
	
	// inform load balancer that the request is ended
	public boolean endService(String serviceName, String serverName, String client, boolean isSuccess) {
		System.out.println("[ REQUEST ]\tClient " + client + " ends " + serviceName + ".");
		return services.endService(serviceName, serverName, client, isSuccess);
	}
}
