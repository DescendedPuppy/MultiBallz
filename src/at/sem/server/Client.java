package at.sem.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{
	
    static public DataInputStream in;
    static public DataOutputStream out;
    private Socket connection;
    private String ip;

    public Client(String remoteMachineIn/*,JoinGame m*/){
        ip = remoteMachineIn;
        startClient();
    }

    private void startClient() {
        try{
            connection = new Socket(ip,6969);
            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());

        }catch (UnknownHostException e){
        	System.out.println("fail");
        }
        catch (IOException except){
        	System.out.println("fail");
        }
    }
}