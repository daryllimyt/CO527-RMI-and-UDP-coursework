/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import common.MessageInfo;

public class UDPClient {

	private DatagramSocket sendSoc;

	public static void main(String[] args) {
		InetAddress	serverAddr = null;
		int			recvPort;
		int 		countTo;
		String 		message;

		// Get the parameters
		if (args.length < 3) {
			System.err.println("Arguments required: server name/IP, recv port, message count");
			System.exit(-1);
		}

		try {
			serverAddr = InetAddress.getByName(args[0]);
			System.out.println(serverAddr.getHostName());
		} catch (UnknownHostException e) {
			System.out.println("Bad server address in UDPClient, " + args[0] + " caused an unknown host exception " + e);
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[1]);
		System.out.println(recvPort);

		countTo = Integer.parseInt(args[2]);


		// TO-DO: Construct UDP client class and try to send messages
		System.out.println("Construct UDP client class to try and send messages...");
		UDPClient udp = new UDPClient();
		System.out.println("Attempting to send messages...");
		for (int i = 0; i < countTo ; i++){
			message = Integer.toString(countTo) + ";" + Integer.toString(i);
			udp.send( message, serverAddr, recvPort);
		}

	}

	public UDPClient() {
		// TO-DO: Initialise the UDP socket for sending data
		try {
			sendSoc = new DatagramSocket();
		} catch (Exception e) {
			System.out.println("Failed to create socket for sending data....");
		}
	}

	private void testLoop(InetAddress serverAddr, int recvPort, int countTo) {
		int				tries = 0;

		// TO-DO: Send the messages to the server
	}

	private void send(String payload, InetAddress destAddr, int destPort) {
		int				payloadSize;
		byte[]				pktData;
		DatagramPacket		pkt;

		// TO-DO: build the datagram packet and send it to the server
		pktData = payload.getBytes();
		payloadSize = pktData.length;
		pkt = new DatagramPacket(pktData, payloadSize, destAddr, destPort);
		try {
			sendSoc.send(pkt);
		} catch (IOException e) {
			System.out.println("Failed to transmit packet over network.");
			System.exit(-1);
		}
	}
}
