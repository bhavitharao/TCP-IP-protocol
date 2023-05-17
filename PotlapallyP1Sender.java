import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// Sender class to perform sender functionality
public class PotlapallyP1Sender {

	/**
	 * This is the main driver program and it will be used to run the main
	 * functionality
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
		Scanner scn = new Scanner(System.in);
		System.out.print("Please enter host name : ");
		String host = scn.nextLine();
		System.out.print("Please enter port number : ");
		int port = Integer.parseInt(scn.nextLine());
		Socket socket = new Socket(host, port);
		System.out.println("Connected to the server at host: " + host + ", port: " + port);
		while (true) {
			System.out.println("Username : ");
			String username = scn.nextLine();
			System.out.println("Password : ");
			String password = scn.nextLine();
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(username + "," + password);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			System.out.println(inputStream.readObject());
			System.out.println("Items:");
			while (true) {
				String data = scn.nextLine();
				outputStream = new ObjectOutputStream(socket.getOutputStream());
				outputStream.writeObject(data);
				inputStream = new ObjectInputStream(socket.getInputStream());

				System.out.println(inputStream.readObject());
			}

		}
	
	}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
