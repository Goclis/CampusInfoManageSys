package common.vo.schoolroll;//实体类

import java.io.Serializable;

public class StudentInfo implements Serializable
{
	private String stuNum;//学号
	private String stuId;//一卡通号
	private String college;//学院
	private String major;//专业
	private String entryDate;//入学时间
	private String stuName;//名字
	private String stuPoliticalLandscape;//政治面貌
	private Long stuPhoneNum;//联系方式
	private String stuSex;//性别
	private String ID;//身份证
	private String stuBirthday;//生日
	private String stuNation;//民族
	private String stuHomeTown;//籍贯
	private String stuAddress;
	public StudentInfo(String stuNum,String stuId,String college,String major
	 ,String entryDate,String stuName,String stuPoliticalLandscape,long stuPhoneNum2
	 ,String stuSex, String stuID,String stuBirthday, String stuNation,String stuHomeTown,String stuAddress) 
   {
		this.stuNum = stuNum;
		this.stuId = stuId;
		this.college = college;
		this.major = major;
		this.entryDate = entryDate;
		this.stuName = stuName;
		this.stuPoliticalLandscape = stuPoliticalLandscape;
		this.stuPhoneNum = stuPhoneNum2;	
		this.stuSex = stuSex;
		this.ID = stuID;
		this.stuBirthday = stuBirthday;
		this.stuNation = stuNation;
		this.stuHomeTown = stuHomeTown;	
		this.stuAddress = stuAddress;
	}
	public StudentInfo(String stuNum)
	{
		this.stuNum = stuNum;
	}
	public String getStuNum() {
		return stuNum;
	}
	public String getStuId() {
		return stuId;
	}
	public String getCollege() {
		return college;
	}
	public String getMajor() {
		return major;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public String getStuName() {
		return stuName;
	}
	public String getStuPoliticalLandscape() {
		return stuPoliticalLandscape;
	}
	public Long getStuPhoneNum() {
		return stuPhoneNum;
	}
	public String getStuSex() {
		return stuSex;
	}
	public String getStuID() {
		return ID;
	}
	public String getStuNation() {
		return stuNation;
	}
	public String getStuBirthday() {
		return stuBirthday;
	}
	public String getStuHomeTown() {
		return stuHomeTown;
	}
	public String getStuAddress() {
		return stuAddress;
	}

}
