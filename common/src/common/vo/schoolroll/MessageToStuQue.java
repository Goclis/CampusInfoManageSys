package common.vo.schoolroll;

import java.io.Serializable;

public class MessageToStuQue implements Serializable {
	private String stuQue;
	private int flag;

	public MessageToStuQue(String stuQue, int flag) {
		this.stuQue = stuQue;
		this.flag = flag;
	}

	public String getStuQue() {
		return stuQue;
	}

	public int getFlag() {
		return flag;
	}

}
