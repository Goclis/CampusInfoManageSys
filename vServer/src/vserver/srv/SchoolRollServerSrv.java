/**
 * 
 */
package vserver.srv;

import vserver.dao.SchoolRollDbOperator;
import common.util.MessageType;
import common.vo.Message;
import common.vo.schoolroll.MessageToStuDel;
import common.vo.schoolroll.MessageToStuQue;
import common.vo.schoolroll.StudentInfo;

/**
 * 学籍管理服务器端服务类
 * @author goclis
 */
public class SchoolRollServerSrv {
	/**
	 * 新增学生信息
	 * @param stuInfo
	 * @return
	 */
	public Message addNewStuInfo(StudentInfo stuInfo) {
		SchoolRollDbOperator dbOperator = new SchoolRollDbOperator();
		Message msgRt = new Message(MessageType.SCHOOLROLL_ADD_STU);
		if (dbOperator.addNewStuInfo(stuInfo)) {
			msgRt.setData(1);
		} else {
			msgRt.setData(0);
		}
		
		return msgRt;
	}
	
	/**
	 * 查询学生信息
	 * @param stuQuery
	 * @return
	 */
	public Message queryStuInfo(MessageToStuQue stuQuery) {
		SchoolRollDbOperator dbOperator = new SchoolRollDbOperator();
		Message msgRt = new Message(MessageType.SCHOOLROLL_QUERY_STU);
		msgRt.setData(dbOperator.queryStuInfo(stuQuery));
		return msgRt;
	}
	
	/**
	 * 删除学生信息
	 * @param stuDel
	 * @return
	 */
	public Message deleteStuInfo(MessageToStuDel stuDel) {
		SchoolRollDbOperator dbOperator = new SchoolRollDbOperator();
		Message msgRt = new Message(MessageType.SCHOOLROLL_DELETE_STU);
		msgRt.setData(dbOperator.deleteStuInfo(stuDel));
		return msgRt;
	}

}
