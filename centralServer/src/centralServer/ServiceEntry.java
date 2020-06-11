/*
 * ServiceEntry.java
 * Haoran Ren & Dayuan Tan
 * CMSC 621 Fall 2018
 * Project - Distributed System
 * 
 * This file defines the ServiceEntry class on the central server.
 * A ServiceEntry class describes an instance of a service type provided by a server.
 * 
 */

package centralServer;

public class ServiceEntry {
	
	public final static int FAILURE_INTERVAL = 20;
	public final static int FAILURE_MAX = 5;

	/*
	 * m_url: the url of the service instance
	 * m_load: the load of the service instance
	 * m_failure: number possible continuous failures since last register
	 * m_lastFailure: time stamp of last possible failure
	 * m_isAvailable: if the service instance is available
	 *
	 */
	public String m_url;
	public int m_load;
	public int m_failure;
	public long m_lastFailure;
	public boolean m_isAvailable;
	
	public ServiceEntry(String url) {
		m_url = url;
		m_load = 0;
		m_failure = 0;
		m_lastFailure = System.currentTimeMillis();
		m_isAvailable = true;
	}
	
	/*
	 * checkFailure(): check if a failure is a possible continuous failure.
	 * A possible continuous failure is a failure which the last failure occurred in 20 seconds
	 * If number of possible continuous failures reaches 5, consider remove the service.
	 * 
	 */
	public boolean checkFailure() {
		long current = System.currentTimeMillis();
		if (((current - m_lastFailure) / 1000) < FAILURE_INTERVAL) {
			m_failure += 1;
			m_lastFailure = current;
		}
		else {
			m_lastFailure = current;
		}
		
		System.out.println("[ SYSTEM ]\tFailure recorded. Possible continous failure: " + m_failure);
		
		if (m_failure == FAILURE_MAX) {
			// block new requests on that instance
			m_isAvailable = false;
			return true;
		}
		else {
			return false;
		}
	}
}
