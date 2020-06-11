/*
 * Client1.java
 * Haoran Ren & Dayuan Tan
 * CMSC 621 Fall 2018
 * Project - Distributed System
 * 
 * This is a client program of the project.
 * It requests 10 random services from the system in sequence.
 * It also records the response time of all the three request and print the average response time.
 * 
 */

package client;

import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class Client1 {

	public final static String CLIENT_NAME = "client3";			// name of the client
	public final static String CENTRAL_SERVER = "http://localhost:8080/centralServer/services/CentralServer";
	public final static String CENTRAL_NAMESPACE = "http://centralServer";
	public final static String CENTRAL_SERVER_B = "http://localhost:8080/centralServerB/services/CentralServerB";
	public final static String CENTRAL_NAMESPACE_B = "http://centralServerB";	
	public final static int SERVICE_NUM = 3;					// total number of services provided by the system

	public static void main(String arg[]) {
		
		String currentCentralServer = CENTRAL_SERVER;
		String currentCentraNamespace = CENTRAL_NAMESPACE;
		
		int serviceNum;					// for random function
		String serviceName;
		String serviceUrl;

		String serverName = "";
		String targetNamespace = "";

		boolean isLoad = false;			// if loads a server, should end the request when service done
		boolean isSuccess = false;		// if service unsuccessful, should report failure
		
		// for web service request
		RPCServiceClient client;
		Options option;
		EndpointReference address;
		QName service;
		Object[] args;
		Class[] value;
		
										// for timer
		int processTime;				// the simulate process delay, remove from response time
		long t_s;						// request start time
		long t_e;						// request end time

		// for phase 1, requesting for the service url
		long p1Sum = 0;
		int p1Num = 0;
		
		// for phase 2, requesting for service
		long p2Sum = 0;
		int processSum = 0;
		int p2Num = 0;
		
		// for phase 3, end requesting
		long p3Sum = 0;
		int p3Num = 0;
		
		
		
		for (int i = 0; i < 20; i++) {
			
			serviceNum = (int) (Math.random() * SERVICE_NUM + 1);
			//serviceNum = 2;

			if (serviceNum == 1) {
				serviceName = "service1";
			}
			else if (serviceNum == 2) {
				serviceName = "service2";
			}
			else {
				serviceName = "service3";
			}
			
			
			serviceUrl = "";
			
			try {
				client = new RPCServiceClient();
				option = client.getOptions();
				address = new EndpointReference(currentCentralServer);
				option.setTo(address);
				
				service = new QName(currentCentraNamespace, "requestService");
				args = new Object[] {serviceName, CLIENT_NAME};
				value = new Class[] {String.class};
				
				System.out.println("[ REQUEST ]\tClient " + CLIENT_NAME + " requests " + serviceName + ".");
				t_s = System.currentTimeMillis();
				serviceUrl = (String) client.invokeBlocking(service, args, value)[0];
				t_e = System.currentTimeMillis();
				p1Sum += (t_e - t_s);
				p1Num += 1;	
			} catch (AxisFault e) {
				currentCentralServer = CENTRAL_SERVER_B;
				currentCentraNamespace = CENTRAL_NAMESPACE_B;
				System.err.println("[ EXCEPTION ]\tAxisFault. Switch to backup server.");
			}

			try {
				if (serviceUrl != "") {
					isLoad = true;
					serverName = serviceUrl.split("/")[3];
					targetNamespace = "http://" + serverName;
					
					client = new RPCServiceClient();
					option = client.getOptions();
					address = new EndpointReference(serviceUrl);
					option.setTo(address);
					
					service = new QName(targetNamespace, serviceName);
					args = new Object[] {CLIENT_NAME};
					value = new Class[] {Integer.class};
					
					System.out.println("[ REQUEST ]\tClient " + CLIENT_NAME + " requests " + serviceName + " on  " + serverName + ".");
					t_s = System.currentTimeMillis();
					processTime = (int) client.invokeBlocking(service, args, value)[0];
					t_e = System.currentTimeMillis();
					
					if (processTime == -1) {
						processTime = 0;
						isSuccess = false;
						System.out.println("[ REQUEST ]\tClient " + CLIENT_NAME + " requests " + serviceName + ". Unuccessful.");
					}
					else {
						isSuccess = true;
						System.out.println("[ REQUEST ]\tClient " + CLIENT_NAME + " requests " + serviceName + ". Successful.");
					}
					p2Sum += (t_e - t_s);
					p2Num += 1;
					processSum += processTime;
				}
				else {
					System.out.println("[ REQUEST ]\tClient " + CLIENT_NAME + " requests " + serviceName + ". Not available.");
				}
				
			} catch (AxisFault e) {
				isSuccess = false;
				System.err.println("[ EXCEPTION ]\tAxisFault.");
			}
			
			if (isLoad) {
				try {
					client = new RPCServiceClient();
					option = client.getOptions();
					address = new EndpointReference(currentCentralServer);
					option.setTo(address);
					
					service = new QName(currentCentraNamespace, "endService");
					args = new Object[] {serviceName, serverName, CLIENT_NAME, isSuccess};
					value = new Class[] {Boolean.class};
					
					System.out.println("[ REQUEST ]\tClient " + CLIENT_NAME + " ends " + serviceName + ".");;
					t_s = System.currentTimeMillis();
					client.invokeBlocking(service, args, value);
					t_e = System.currentTimeMillis();
					p3Sum += (t_e - t_s);
					p3Num += 1;
				} catch (AxisFault e) {
					currentCentralServer = CENTRAL_SERVER_B;
					currentCentraNamespace = CENTRAL_NAMESPACE_B;
					System.err.println("[ EXCEPTION ]\tAxisFault. Switch to backup server.");
				}
			}
		}
		
		int p1Avg = (int) p1Sum / p1Num; 
		int p2Avg = (int) (p2Sum - processSum * 1000) / p2Num;
		int p3Avg = (int) p3Sum / p3Num;
		int avg = p1Avg + p2Avg + p3Avg;
		
		System.out.println("[ SYSTEM ]\trequestService average response time: " + p1Avg + " milliseconds.");
		System.out.println("[ SYSTEM ]\tservice average response time: " + p2Avg + " milliseconds.");
		System.out.println("[ SYSTEM ]\tendService average response time: " + p3Avg + " milliseconds.");
		System.out.println("[ SYSTEM ]\tFull cycle average response time: " + avg + " milliseconds.");
	}
}
