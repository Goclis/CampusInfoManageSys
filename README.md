CampusInfoManageSys
===================

系统设计大致如下:

###客户端:
每个客户端启动的时候都为其建立一个ClientSrvHelper对象对其进行服务管理。
ClientSrvHelper进行服务器的连接，保存相应Socket引用。
ClientSrvHelper通过Message与服务器进行信息交互以及数据变更。
以登录为例：
ClientSrvHelper存在login(User user)方法，方法大致实现如下:

```java
public User login(User user) {
	// 此处暂时忽略异常等处理，即无视try...catch...块
	Message msg = new Message(); // 新建一个Message对象
	msg.setType("登录"); // 设置需要服务器处理的类型，此处为登录
	msg.setData(user); // 设置数据，此处为要登录的用户
	// ... // Message其他具体的属性待讨论
	
	// 用于发送Message的输出流，此处socket为ClientSrvHelper对象创建时保存的Socket对象
	ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream()); 
	os.writeObject(msg); // 将Message写入流中
	os.flush(); // 清空缓冲区保证流中Message即时发出
	
	// 用于接受Message的输入流，socket同上
	ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
	Message msgRet = (Message) is.readObject(); // 得到服务器处理完msg(Message)发回的反馈Message，即处理结果
	// 以下根据msgRet中的状态域来判断是登录成功进入主界面还是登陆失败提示错误信息
	// ...
}
```

###服务器端：
服务器端为每个Socket都建立一个线程用于处理，线程主要负责持续的监听客户端发来的Message，并根据Message的某些域的值来进行操作并给予反馈（即朝客户端发Message）


其余可见代码




###数据库相关


** ci_user ** 表结构与测试数据

+-----------+----------+------+--------+----------+-----+------------+-------+--------+
| id        | password | name | id_num | identity | sex | department | major | status |
+-----------+----------+------+--------+----------+-----+------------+-------+--------+
| 213110000 | 123456   |      |        | 学生     |     |            |       |        |
+-----------+----------+------+--------+----------+-----+------------+-------+--------+

