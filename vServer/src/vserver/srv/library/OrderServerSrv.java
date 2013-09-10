package vserver.srv.library;

import java.sql.SQLException;
import java.util.Vector;

import vserver.dao.library.OrderDao;

import common.util.MessageStatusCode;
import common.util.MessageType;
import common.vo.Message;
import common.vo.library.Book;
import common.vo.library.Order;
import common.vo.library.Reader;

/**
 * order在服务器端的通信
 * 实现取消预约，查找预约，预约书籍
 * @author zhongfang
 */
public class OrderServerSrv {

	private vserver.srv.ServerSrvHelper serverSrvHelper;
	/**
	 * 
	 */
	public OrderServerSrv(vserver.srv.ServerSrvHelper serverSrvHelper2) {
		this.serverSrvHelper=serverSrvHelper2;
	}

	public Message orderBook(Reader reader,Book book){
		if(book==null||reader==null){
			return Message.createFailureMessage();
		}else{
			Order order=null;
			try {
				order=OrderDao.orderBook(reader, book);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt=new Message(MessageType.ORDER_BOOK);
			if(order!=null){
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);;
				msgRt.setData(order);
				serverSrvHelper.close();
			}else{
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message cancelOrder(Order order) {
		if(order==null){
			return Message.createFailureMessage();
		}else{
			try {
				order=OrderDao.cancelOrder(order);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Message msgRt=new Message(MessageType.CANCEL_ORDER);
			if(order!=null){
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);;
				msgRt.setData(order);
				serverSrvHelper.close();
			}else{
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message findOrder(Reader reader) {
		if(reader==null){
			return Message.createFailureMessage();
		}else{
			Vector v=new Vector(0);
			try {
				v=OrderDao.getOrders(reader);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Message msgRt=new Message(MessageType.FIND_ORDER);
			if(v.size()!=0){
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);;
				msgRt.setData(v);
				serverSrvHelper.close();
			}else{
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}
}
