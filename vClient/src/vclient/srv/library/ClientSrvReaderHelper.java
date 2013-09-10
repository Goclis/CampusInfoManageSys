package vclient.srv.library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import common.util.MessageType;
import common.util.ObjectTransformer;
import common.vo.Message;
import common.vo.library.Reader;

/**
 * 帮助reader类在客户端方面的通信 有reader的增。删。改。查和挂失读者证
 * 
 * @author zhongfang
 */
public class ClientSrvReaderHelper {
	private Socket socket;
	private String host = "localhost";
	private int port = 8000;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;

	public ClientSrvReaderHelper() {
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Reader addReader(Reader reader) {
		Message msgaddReader = new Message(MessageType.READER_ADD);
		msgaddReader.setData(reader);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgaddReader);
			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getReader(msgBack.getData());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Reader modifyReader(Reader reader) {
		Message msgmodifyReader = new Message(MessageType.READER_MODIFY);
		msgmodifyReader.setData(reader);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgmodifyReader);
			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getReader(msgBack.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Reader deleteReader(Reader reader) {
		// Message msgaddReader=Message.deleteReaderMessage(reader);
		Message msgdeleteReader = new Message(MessageType.READER_DELETE);

		msgdeleteReader.setData(reader);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgdeleteReader);
			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getReader(msgBack.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Vector findReader(Reader reader, Integer findType) {
		Message msgfindReader;
		msgfindReader = new Message(MessageType.READER_FIND);
		msgfindReader.setFindType(findType);
		msgfindReader.setData(reader);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgfindReader);
			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getVector(msgBack.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Reader reportLossReader(Reader reader) {
		Message msglossReader;
		msglossReader = new Message(MessageType.REPORT_LOSS_OF_READER);
		msglossReader.setData(reader);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msglossReader);
			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getReader(msgBack.getData());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
