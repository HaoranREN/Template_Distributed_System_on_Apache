/*
 * Server1Register.java
 * Haoran Ren & Dayuan Tan
 * CMSC 621 Fall 2018
 * Project - Distributed System
 * 
 * This file includes a function to register services provided by server1 to the central server.
 * Server1 provides service1 and service2.
 * 
 */

package server1;

import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;


public class Server1Register {

	public final static String CENTRAL_SERVER = "localhost";	// central server ip address
	public final static String CENTRAL_SERVER_B = "localhost";	// backup central server ip address
	public final static String LOCAL_HOST = "localhost";		// server's ip address
	
	public static void main(String args[]) {
		
		try {
			RPCServiceClient client = new RPCServiceClient();
			Options option = client.getOptions();
			EndpointReference address = new EndpointReference("http://" + CENTRAL_SERVER + ":8080/centralServer/services/CentralServer");
			option.setTo(address);

			QName service1 = new QName("http://centralServer", "registerService");
			Object[] args1 = new Object[] {"service1", "server1", "http://" + LOCAL_HOST + ":8080/server1/services/Server1"};
			Class[] value1 = new Class[] {boolean.class};
			
			System.out.println("[ REGISTRY ]\tServer1 registers service1.");
			if ((boolean) client.invokeBlocking(service1, args1, value1)[0]) {
				System.out.println("[ SYSTEM ]\tService1 on server1 registered.");
			}
			
			QName service2 = new QName("http://centralServer", "registerService");
			Object[] args2 = new Object[] {"service2", "server1", "http://" + LOCAL_HOST + ":8080/server1/services/Server1"};
			Class[] value2 = new Class[] {boolean.class};
			
			System.out.println("[ REGISTRY ]\tServer1 registers service2.");
			if ((boolean) client.invokeBlocking(service2, args2, value2)[0]) {
				System.out.println("[ SYSTEM ]\tService2 on server1 registered.");
			}
			
		} catch (AxisFault e) {
			System.err.println("[ EXCEPTION ]\tAxisFault.");
		}
		
		try {
			RPCServiceClient client = new RPCServiceClient();
			Options option = client.getOptions();
			EndpointReference address = new EndpointReference("http://" + CENTRAL_SERVER_B + ":8080/centralServerB/services/CentralServerB");
			option.setTo(address);

			QName service1 = new QName("http://centralServerB", "registerService");
			Object[] args1 = new Object[] {"service1", "server1", "http://" + LOCAL_HOST + ":8080/server1/services/Server1"};
			Class[] value1 = new Class[] {boolean.class};
			
			System.out.println("[ REGISTRY ]\tServer1 registers service1 to backup central server");
			if ((boolean) client.invokeBlocking(service1, args1, value1)[0]) {
				System.out.println("[ SYSTEM ]\tService1 on server1 registered at backup central server.");
			}
			
			QName service2 = new QName("http://centralServerB", "registerService");
			Object[] args2 = new Object[] {"service2", "server1", "http://" + LOCAL_HOST + ":8080/server1/services/Server1"};
			Class[] value2 = new Class[] {boolean.class};
			
			System.out.println("[ REGISTRY ]\tServer1 registers service2 to backup central server.");
			if ((boolean) client.invokeBlocking(service2, args2, value2)[0]) {
				System.out.println("[ SYSTEM ]\tService2 on server1 registered at backup central server.");
			}
			
		} catch (AxisFault e) {
			System.err.println("[ EXCEPTION ]\tAxisFault.");
		}
	}
}
