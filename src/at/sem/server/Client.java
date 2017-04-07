package at.sem.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import at.sem.JoinGame;

public class Client{
	
    static public DataInputStream in;
    static public DataOutputStream out;
    private Socket connection;
    private String ip;
    public JoinGame m;

    public Client(String remoteMachineIn,JoinGame m){
        ip = remoteMachineIn;
        this.m=m;
        startClient();
    }

    private void startClient() {
        try{
            connection = new Socket(ip,6969);
            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());
            while(connection.isConnected())
            	m.outcome(in.readUTF(), null);
        }catch (UnknownHostException e){
        	System.out.println("fail");
        }
        catch (IOException except){
        	System.out.println("fail");
        }
    }
    
    public void send(String msg) throws IOException
    {
    	out.writeUTF(msg);
    	out.flush();
    }
}