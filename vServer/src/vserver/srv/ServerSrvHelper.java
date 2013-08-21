package vserver.srv;

import goclis.beans.Message;
import goclis.beans.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSrvHelper {
	private int port = 8000;
	
	public static void main(String[] args) {
		new ServerSrvHelper();
	}
	
	public ServerSrvHelper() {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Server Start");
			while (true) {
				Socket socket = server.accept(); // 新连接
				System.out.println("New Link");
				/*ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
				try {
					System.out.println(((Message) fromClient.readObject()).getName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				//new Thread(new DealMessages(socket)).start();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 用于处理与客户端交互的类
	 * @author goclis
	 *
	 */
	private class DealMessages implements Runnable {
		private Socket socket;
		private ObjectInputStream inputFromClient;
		private ObjectOutputStream outputToClient;
		
		public DealMessages() {
			socket = null;
		}
		
		public DealMessages(Socket socket) {
			this.socket = socket;
		}
		
		/**
		 * 监听客户端通过Socket发来的Message
		 */
		@Override
		public void run() {
			try {
				inputFromClient = new ObjectInputStream(socket.getInputStream());
				outputToClient = new ObjectOutputStream(socket.getOutputStream());
				while (true) {
					System.out.println("in while");
					Message msg = (Message) inputFromClient.readObject();
					System.out.println(msg.getUid());
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void dealMessage(Message msg) {
			switch (msg.getType()) {
			case 1: // 注册
				System.out.println("register");
				User user = (User) msg.getData();
				System.out.println(user.getId() + " " + user.getPassword() + " "
						+ user.getSex() + " " + user.getName() + user.getIdNum() 
						+ user.getDepartment() + " " + user.getMajor() + user.getIdentity());
				break;
			}
		}
	}
}
