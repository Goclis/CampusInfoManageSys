package vclient.srv;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import common.beans.Good;
import common.beans.User;

public class ClientSrvHelper {
	private Socket socket;
	private String host = "localhost";
	private int port = 8000;
	
	public ClientSrvHelper() {
		// 创建socket保持联系
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			System.out.println("不确定的主机");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("客户端连接服务器失败");
			e.printStackTrace();
		}
	}
	
	// ---------- 用户管理模块 BEGIN -----------
	
	/**
	 * 使用UserManageClientSrv执行注册服务
	 * @param user
	 * @return
	 */
	public User register(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv(socket);
		return uMClientSrv.register(user);
	}
	
	/**
	 * 使用UserManageClientSrv执行登录服务
	 * @param user
	 * @return
	 */
	public User login(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv(socket);
		return uMClientSrv.login(user);
	}
	
	/**
	 * 使用UserManageClientSrv执行登出服务
	 * @param user
	 * @return
	 */
	public User logout(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv(socket);
		return uMClientSrv.logout(user);
	}

	// ------------ 用户管理模块 END ---------------
	
	// ------------ 商店模块 BEGIN -----------------
	
	/**
	 * 添加商品
	 */
	public Good addNewGood(Good good) {
		return null;
	}
	
	// ------------ 商店模块 ENG -------------------
}
