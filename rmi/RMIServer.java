/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		// On receipt of first message, initialise the receive buffer
		if (receivedMessages == null || msg.totalMessages != totalMessages) {
			totalMessages = msg.totalMessages;
			receivedMessages = new int[msg.totalMessages];
		}
		// Log receipt of the message
		receivedMessages[msg.messageNum] = 1;

		// If this is the last expected message, then identify any missing messages
		if (msg.messageNum + 1 == totalMessages) {
			System.out.println("Reviewing messages...");
			String s = "Lost packets: ";
			int count = 0;
			for (int i = 0; i < totalMessages; i++) {
				if (receivedMessages[i] != 1) {
					count++;
					s += " " + (i+1) + ", ";
				}
			}
			if (count == 0) s += "None";

			System.out.println("Total messages sent:\t" + totalMessages);
			System.out.println("Total messages received:\t" + (totalMessages - count);
			System.out.println("Total messages lost:\t" + count);
			System.out.println(str);
			System.out.println("Running...");
			

		}
	}


	public static void main(String[] args) {

		RMIServer rmis = null;

		// Initialise Security Manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		// Instantiate the server class
		try {
			rmis = new RMIServer();
		} catch (RemoteException e) {
			System.out.println("Error instantiating server class");
			System.exit(-1);
		}

		// Bind to RMI registry
		try {
			rebindServer("RMIServer", rmis );
			System.out.println("Running...");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

	protected static void rebindServer(String serverURL, RMIServer server) {

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
		try {
			LocateRegistry.createRegistry(8000);
			Naming.rebind(serverURL, server);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
		try {
			Naming.rebind(serverURL, server);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
