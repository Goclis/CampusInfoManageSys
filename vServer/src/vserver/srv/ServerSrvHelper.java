package vserver.srv;

import goclis.beans.Message;
import goclis.beans.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSrvHelper implements Runnable {
	private int port = 8000;
	private Socket socket;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	
	public static void main(String[] args) {
		new ServerSrvHelper();
	}
	
	public ServerSrvHelper() {
		
	}

	public ServerSrvHelper(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while (true) {
			try {
				fromClient = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) fromClient.readObject();
				System.out.println(msg.getName());
				
				toClient = new ObjectOutputStream(socket.getOutputStream());
				Message msgRet = new Message();
				msgRet.setName("反馈");
				toClient.writeObject(msgRet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
