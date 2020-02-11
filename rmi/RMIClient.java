/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.MessageInfo;

public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		// Initialise Security Manager
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());

		try {
			// Bind to RMIServer
			Registry registry = LocateRegistry.getRegistry(args[0], 1099);
			iRMIServer = (RMIServerI) registry.lookup(urlServer);

			// Attempt to send messages the specified number of times
			for (int i = 0; i < numMessages; i++) {
				MessageInfo message = new MessageInfo(numMessages, i);
				iRMIServer.receiveMessage(message);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
