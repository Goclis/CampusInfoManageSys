package vserver.view;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.beans.Message;

import vserver.srv.ServerSrvHelper;

public class Server {
	private int port = 8000;
	
	// threads 保存为每个客户端所创建的线程
	private HashMap<Long, ServerSrvHelper> threads; 
	
	// 线程池
	private ExecutorService threadPools = Executors.newCachedThreadPool();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Server();
	}
	
	public Server() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Start");
			while (true) {
				Socket socket = serverSocket.accept();
				threadPools.execute(new ServerSrvHelper(socket));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
