/**
 * 
 */
package vclient.srv;

import java.io.ObjectOutputStream;

import common.util.MessageType;
import common.util.ObjectTransformer;
import common.vo.Message;
import common.vo.schoolroll.MessageToStuDel;
import common.vo.schoolroll.MessageToStuQue;
import common.vo.schoolroll.StudentInfo;

/**
 * 学籍管理模块的客户端服务类
 * @author goclis
 */
public class SchoolRollClientSrv extends ClientService {
	/**
	 * 新增学生信息
	 * @param stuInfor
	 * @return 返回1表示成功，其他为失败
	 */
	public int increatStuInfo(StudentInfo stuInfor) {
		if (socket != null) {
			Message msg = new Message(MessageType.SCHOOLROLL_ADD_STU, stuInfor);
			Message msgBack = sendMessage(msg, "新增学生信息");
			return ObjectTransformer.getInteger(msgBack.getData());
		}
		return 0;
	}
	
	/**
	 * 查询学生信息
	 * @param obj
	 * @return 返回封装后的StudentInfo
	 */
	public StudentInfo queryStudentInfo(MessageToStuQue obj) {
		if (socket != null) {
			Message msg = new Message(MessageType.SCHOOLROLL_QUERY_STU, obj);
			Message msgBack = sendMessage(msg, "查询学生信息");
			return ObjectTransformer.getObjStu(msgBack.getData());
		}
		return null;
	}
	
	/**
	 * 删除学生信息
	 * @param messageToStuDel
	 * @return 成功返回删除的数目，返回0则为失败
	 */
	public int deleteStuInfo(MessageToStuDel messageToStuDel) {
		if (socket != null) {
			Message msg = new Message(MessageType.SCHOOLROLL_DELETE_STU, messageToStuDel);
			Message msgBack = sendMessage(msg, "删除学生信息");
			return ObjectTransformer.getInteger(msgBack.getData());
		}
		return 0;
	}
	
}
