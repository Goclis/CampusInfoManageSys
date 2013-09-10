package vserver.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;

import vserver.srv.ServerSrvHelper;

public class ServerGUI extends JFrame implements ActionListener {
	/**
	 * 内部类，真正的Server
	 * @author goclis
	 */
	private class Server implements Runnable {
		private int port = 8000;
		private boolean closed = true;
		private ServerSocket serverSocket;

		// 线程池
		private ExecutorService threadPools = Executors.newCachedThreadPool();

		public boolean isClosed() {
			return closed;
		}

		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(port);
				System.out.println("Server Start");
				while (!closed) {
					Socket socket = serverSocket.accept();
					threadPools.execute(new ServerSrvHelper(socket));
				}
			} catch (IOException e) {
				if (!closed) {
					System.out.println("Error At Server GUI");
					e.printStackTrace();
				}
			} finally {
				try {
					serverSocket.close();
					// System.out.println("Server Closed");
				} catch (IOException e) {
					System.out.println("Error When Close Server");
					e.printStackTrace();
				}
			}
		}

		public void setClosed(boolean closed) {
			this.closed = closed;
			if (this.closed == true) {
				try {
					serverSocket.close();
					System.out.println("Server Closed");
				} catch (IOException e) {
					System.out.println("Error When Close Server");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Main Function
	 * @param args
	 */
	public static void main(String[] args) {
		new ServerGUI();
	}

	JButton jbtStart = new JButton("开启服务器");
	JButton jbtStop = new JButton("关闭服务器");
	private Server server = new Server();
	
	public ServerGUI() {
		this.setLayout(new GridLayout(2, 1, 5, 5));
		this.add(jbtStart);
		this.add(jbtStop);
		jbtStart.addActionListener(this);
		jbtStop.addActionListener(this);

		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtStart) {
			startServer();
		} else if (e.getSource() == jbtStop) {
			closeServer();
		}
		jbtStart.setEnabled(server.isClosed());
	}
	
	/**
	 * 关闭服务器
	 */
	private void closeServer() {
		server.setClosed(true);
	}

	/**
	 * 开启服务器
	 */
	private void startServer() {
		new Thread(server).start();
		server.setClosed(false);
	}
}
