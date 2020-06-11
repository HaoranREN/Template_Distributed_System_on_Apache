/*
 * ServicesList.java
 * Haoran Ren & Dayuan Tan
 * CMSC 621 Fall 2018
 * Project - Distributed System
 * 
 * This file defines the ServicesList class on the central server.
 * A ServicesList class describes all the services provided by the system.
 * It contains all the instances of all the services on different servers as ServiceEntry class.
 * It also contains load of each service instances as reference for the load balancing
 * 
 */

package centralServerB;

import java.util.Map;
import java.util.HashMap;

public class ServicesList {
	
	/*
	 * m_services: a HashMap contains all the services instances in the system.
	 * The key is String type of the service name.
	 * The value is a HashMap<string, ServiceEntry> type.
	 * 
	 */
	public HashMap<String, HashMap<String, ServiceEntry>> m_services;
	public HashMap<String, Integer> m_load;
	
	public ServicesList() {
		m_services = new HashMap<String, HashMap<String, ServiceEntry>>();
		m_load = new HashMap<String, Integer>();
		System.out.println("[ SYSTEM ] [BACKUP]\tCentral server started.");
	}
	
	public boolean registerService(String serviceName, String serverName, String url) {
		if (m_services.containsKey(serviceName)) {
			ServiceEntry instance = new ServiceEntry(url);
			m_services.get(serviceName).put(serverName, instance);
		}
		else {
			ServiceEntry instance = new ServiceEntry(url);
			HashMap<String, ServiceEntry> instanceList = new HashMap<String, ServiceEntry>();
			instanceList.put(serverName, instance);
			m_services.put(serviceName, instanceList);
		}
		if (!m_load.containsKey(serverName)) {
			m_load.put(serverName, 0);
		}
		System.out.println("[ SYSTEM ] [BACKUP]\t" + serviceName + " on " + serverName + " registered.");
		
		return true;
	}
	
	public boolean removeService(String serviceName, String serverName) {
		// block new requests on that instance
		if (m_services.get(serviceName).get(serverName).m_isAvailable) {
			m_services.get(serviceName).get(serverName).m_isAvailable = false;
		}
		
		while (m_services.get(serviceName).get(serverName).m_load != 0) {
			// wait for every request on the service instance ends
		}
		
		m_services.get(serviceName).remove(serverName);
		System.out.println("[ SYSTEM ] [BACKUP]\t" + serviceName + " on " + serverName + " removed.");
		
		if (m_services.get(serviceName).isEmpty()) {
			m_services.remove(serviceName);
			System.out.println("[ SYSTEM ] [BACKUP]\tAll " + serviceName + " removed.");
		}
		
		boolean noLoad = true;
		for (Map.Entry<String, HashMap<String, ServiceEntry>> instanceList : m_services.entrySet()) {
			if (instanceList.getValue().containsKey(serverName)) {
				noLoad = false;
			}
		}
		if (noLoad) {
			m_load.remove(serverName);
		}
		
		return true;
	}
	
	public void recordFailure(String serviceName, String serverName) {
		System.out.println("[ SYSTEM ] [BACKUP]\tFailure: " + serviceName + " on " + serverName + " recording.");
		if (m_services.get(serviceName).get(serverName).checkFailure()) {
			removeService(serviceName, serverName);
		}
	}
	
	public String requestService(String serviceName, String client) {
		String url = "";
		
		if (m_services.containsKey(serviceName)) {
			
			/* load balancer*/
			String min_instance_key = "";
			int min_load = 99999999;
			int load_temp;
			HashMap<String, ServiceEntry> instanceList = m_services.get(serviceName);
			for (Map.Entry<String, ServiceEntry> instance : instanceList.entrySet()) {
				if (instance.getValue().m_isAvailable) {
					load_temp = m_load.get(instance.getKey());
					if (load_temp < min_load) {
						min_load = load_temp;
						min_instance_key = instance.getKey();
					}
				}
			}
			/* load balancer end*/
			
			if (instanceList.containsKey(min_instance_key)) {
				m_load.put(min_instance_key, m_load.get(min_instance_key) + 1);
				instanceList.get(min_instance_key).m_load += 1;
				url = instanceList.get(min_instance_key).m_url;
				System.out.println(m_load);
				System.out.println("[ SYSTEM ] [BACKUP]\tClient " + client + " requests " + serviceName + ". Forwarded to " + min_instance_key + ".");
			}
			else {
				System.out.println("[ SYSTEM ] [BACKUP]\tClient " + client + " requests " + serviceName + ". Not available.");
			}
		}
		else {
			System.out.println("[ SYSTEM ] [BACKUP]\tClient " + client + " requests " + serviceName + ". Not available.");
		}
		return url;
	}
	
	// inform load balancer that the request is ended
	public boolean endService(String serviceName, String serverName, String client, boolean isSuccess) {
		if (isSuccess) {
			m_load.put(serverName, m_load.get(serverName) - 1);
			m_services.get(serviceName).get(serverName).m_load -= 1;
			System.out.println("[ SYSTEM ] [BACKUP]\tClient " + client + " ends " + serviceName + ". Successful.");
		}
		// if the request is unsuccessful, records that failure
		else {
			m_load.put(serverName, m_load.get(serverName) - 1);
			m_services.get(serviceName).get(serverName).m_load -= 1;
			System.out.println("[ SYSTEM ] [BACKUP]\tClient " + client + " ends " + serviceName + ". Ussuccessful.");
			recordFailure(serviceName, serverName);
		}
		return true;
	}
}
