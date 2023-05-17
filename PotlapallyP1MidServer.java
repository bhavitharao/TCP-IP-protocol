import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;


public class PotlapallyP1MidServer {
    private ServerSocket middleServer;
    String host = "localhost";
    private ServerStart serverStart;
    int port = 0000;
    boolean isLogged = false;
    PotlapallyP1GroupServer groupServer = null;
    public PotlapallyP1MidServer(int serverPort){
        port = serverPort;
    }
    // Function to start the middle server
    private void startServer(){
        try {
            middleServer = new ServerSocket(port);
            serverStart = new ServerStart();
            serverStart.start();
            host = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Host = " + host + ", Port = " + port);
            System.out.println("Server started at port: " + port);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    // Function to close the middle server.
    public void closeServer() {
        try {
            serverStart.stop();
            middleServer.close();
            System.out.println("Server stopped");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    // This function will check the login details
    public String verifyCredentials(String username, String password) throws IOException
    {
    	String data = "";
    	FileReader fileReader = new FileReader(new File ("userList.txt"));
    	BufferedReader bufferedReader = new BufferedReader(fileReader);
    	while ((data = bufferedReader.readLine())!= null)
    	{
    		String []result = data.split(",");
    		
    		String uName = result[0];
    		String pWord = result[1];
    		String group = result[2];
    		System.out.println(group);
    		if (uName.equals(username) && pWord.equals(password))
    		{
    			return group;
    		}
    	}
    	
    	return "";
    	
    }
    
    // Class to start listening to server
    class ServerStart extends Thread{
        
        public ServerStart() {
            super();
        }
         
        public void run() {
            try {
                Socket clientSocket = middleServer.accept();
                System.out.println("Client connected to the server");
                while(true) {

                    ObjectInputStream i = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream o = new ObjectOutputStream(clientSocket.getOutputStream());
                	if(!isLogged)
                	{
                		String loginDetails = (String)i.readObject();
                        String response = "Login details do not match..";
                        String username = loginDetails.split(",")[0];
                        String password = loginDetails.split(",")[1];
                        String group = verifyCredentials(username, password);
                        if (group == "")
                        	o.writeObject(response);
                        else
                        {
                        	isLogged = true;
                        	o.writeObject("Login successful as "+group+" server.");
                        	groupServer = new PotlapallyP1GroupServer(host, port, group);
                        }
                	}
                	else
                	{
                		String item = (String)i.readObject();
                		if (item.equals("CLOSE"))
                		{
                			closeServer();
                		}
                		System.out.println("Client Asking for: " + item);
                		o.writeObject(item + " bought.");
                	}
                	
                    
                   
                }
            }catch (EOFException | SocketException e) {     
            	System.out.println("A client disconnected from the server");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main (String []args)
    {
    	
          PotlapallyP1MidServer middleServer = new PotlapallyP1MidServer(13966);
          middleServer.startServer();
    }
}


