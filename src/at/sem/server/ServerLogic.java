package at.sem.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerLogic{
	
	static public DataOutputStream out;
	static public DataInputStream in;
	
	public static void run() {
		ServerSocket servSocket;
		try {
			System.out.println(InetAddress.getLocalHost());
			servSocket = new ServerSocket(6969, 0, InetAddress.getLocalHost());
			Socket socket=servSocket.accept();
			in=new DataInputStream(socket.getInputStream());
			out=new DataOutputStream(socket.getOutputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ServerLogic()
	{
		run();
	}
}
