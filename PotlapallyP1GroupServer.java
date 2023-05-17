import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class will serve as the receiver program and will be used to receive the 
 * shopping information from the user and make the sale.
 *
 */
public class PotlapallyP1GroupServer {

    private Socket groupServerSocket;
    
    String host = "";
    int port = 0;
    String group = "";
    
    public PotlapallyP1GroupServer(String host, int port, String group) {
		this.host = host;
		this.port = port;
		this.group = group;
	}
    
     
    // This will receive data from the middle server
    public void processPurchase() throws IOException, ClassNotFoundException{
		// Exception will be thrown if there is some issue in sending the message to the socket 
        Object result = null;
        ObjectInputStream inputStream = new ObjectInputStream(groupServerSocket.getInputStream());
        result = inputStream.readObject();
        System.out.println("IM  " + result);
        ObjectOutputStream outputStream = new ObjectOutputStream(groupServerSocket.getOutputStream());
        outputStream.writeObject(result + " bought."); 
       
    }
    

    
    
}
