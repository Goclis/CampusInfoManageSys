package vserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import common.vo.schoolroll.MessageToStuDel;
import common.vo.schoolroll.MessageToStuQue;
import common.vo.schoolroll.StudentInfo;

/**
 * 学籍管理模块的数据库操作类
 * @author goclis
 */
public class SchoolRollDbOperator {
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	
	/**
	 * 处理删除学生信息
	 * @author goclis
	 */
	public class SchoolRollDeleteStuInfo {
		private int stuFlag;
		private String stuDel;

		public SchoolRollDeleteStuInfo(MessageToStuDel obj) {
			stuFlag = obj.getFlag();
			stuDel = obj.getStuQue();
		}

		// 连接数据库，查询
		public int connectDB() {
			try {
				Class.forName(DRIVER_NAME);

				Connection conn = DriverManager.getConnection(CONN_URL,
						USER_NAME, PASSWORD);
				Statement st;
				st = (Statement) conn.createStatement();
				if (stuFlag == 2)// 按姓名删除
				{
					String sql = "delete from ci_stuInfo  where stuName= '"
							+ stuDel + "'";// 删除数据的sql语句
					int count = st.executeUpdate(sql);
					System.out.println(count);
					return count;
				} else if (stuFlag == 1)// 按学号查询
				{
					String sql = "delete from ci_stuInfo  where stuNum='"
							+ stuDel + "'";// 删除数据的sql语句
					int count = st.executeUpdate(sql);
					System.out.println(count);
					return count;
				}
				st.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return 0;
		}
	}
	
	/**
	 * 处理插入学生信息
	 * @author goclis
	 */
	public class SchoolRollIncreatStuInfo {
		String stuNum;
		String stuId;
		String college;
		String major;
		String entryDate;
		String stuName;
		String stuPoliticalLandscape;
		long stuPhoneNum;
		String stuSex;
		String stuID;
		String stuBirthday;
		String stuNation;
		String stuHomeTown;
		String stuAddress;
		
		public SchoolRollIncreatStuInfo(StudentInfo s) {
			stuNum = s.getStuNum();
			stuId = s.getStuId();
			college = s.getCollege();
			major = s.getMajor();
			entryDate = s.getEntryDate();
			stuName = s.getStuName();
			stuPoliticalLandscape = s.getStuPoliticalLandscape();
			stuPhoneNum = s.getStuPhoneNum();
			stuSex = s.getStuSex();
			stuID = s.getStuID();
			stuBirthday = s.getStuBirthday();
			stuNation = s.getStuNation();
			stuHomeTown = s.getStuHomeTown();
			stuAddress = s.getStuAddress();

		}

		// 连接数据库
		public boolean connectDB() {
			boolean status = true;
			try {
				Class.forName(DRIVER_NAME);

				Connection conn = DriverManager
						.getConnection(CONN_URL, USER_NAME, PASSWORD);
				Statement st;
				String sql = "select * from ci_stuInfo";
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
				while (rs.next()) {
					if (rs.getString("stuNum").equals(stuNum)
							| rs.getString("ID").equals(stuID)) {
						status = false;
						return status;
					}
				}
				PreparedStatement ps;
				String sqlInsert = String
						.format("insert into ci_stuInfo values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
								stuId, stuNum, college, major, entryDate,
								stuName, stuPoliticalLandscape, stuPhoneNum,
								stuSex, stuID, stuBirthday, stuNation,
								stuHomeTown, stuHomeTown);
				ps = conn.prepareStatement(sqlInsert);
				/*ps = conn
						.prepareStatement("insert into ci_stuInfo (stuNum,stuId,college,major,"
								+ "entryDate,stuName,stuPoliticalLandscape,stuPhoneNum,stuSex,ID,stuBirthday,stuNation,"
								+ "stuHomeTown,stuAddress) values ('"
								+ stuNum
								+ "','"
								+ stuId
								+ "','"
								+ college
								+ "','"
								+ major
								+ "', '"
								+ entryDate
								+ "','"
								+ stuName
								+ "','"
								+ stuPoliticalLandscape
								+ "','"
								+ stuPhoneNum
								+ "','"
								+ stuSex
								+ "',"
								+ "'"
								+ stuID
								+ "','"
								+ stuBirthday
								+ "','"
								+ stuNation
								+ "','"
								+ stuHomeTown + "','" + stuAddress + "')");*/
				

				ps.executeUpdate();
				ps.close();
				rs.close();
				conn.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return status;
		}
	}
	
	/**
	 * 处理查询学生信息
	 * @author goclis
	 */
	public class SchoolRollQueryStuInfo {
		private int stuFlag;
		private String stuQue;

		public SchoolRollQueryStuInfo(MessageToStuQue obj) {
			stuFlag = obj.getFlag();

			stuQue = obj.getStuQue();
		}

		// 连接数据库，查询
		public StudentInfo connectDB() {
			StudentInfo s = null;
			try {
				Class.forName(DRIVER_NAME);

				Connection conn = DriverManager
						.getConnection(CONN_URL, USER_NAME, PASSWORD);
				Statement st;
				String sql = "select * from ci_stuInfo";
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
				if (stuFlag == 2)// 按姓名查找
				{
					while (rs.next()) {
						String name = rs.getString(6);
						if (stuQue.equals(name)) {
							s = new StudentInfo(rs.getString(2), rs.getString(1),
									rs.getString(3), rs.getString(4),
									rs.getString(5), name, rs.getString(7),
									rs.getLong(8), rs.getString(9),
									rs.getString(10), rs.getString(11),
									rs.getString(12), rs.getString(13),
									rs.getString(14));
							return s;
						}
					}
				} else if (stuFlag == 1)// 按学号查询
				{
					while (rs.next()) {
						String stuNum = rs.getString(2);
						if (stuQue.equals(stuNum)) {
							s = new StudentInfo(stuNum, rs.getNString(1),
									rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getString(6),
									rs.getString(7), rs.getLong(8),
									rs.getString(9), rs.getString(10),
									rs.getString(11), rs.getString(12),
									rs.getString(13), rs.getString(14));
							return s;
						}
					}
				}
				st.close();
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return s;
		}
	}
	
	/**
	 * 添加学生信息
	 * @param stuInfo
	 * @return
	 */
	public boolean addNewStuInfo(StudentInfo stuInfo) {
		return new SchoolRollIncreatStuInfo(stuInfo).connectDB();
	}

	/**
	 * 查询学生信息
	 * @param stuQuery
	 * @return
	 */
	public StudentInfo queryStuInfo(MessageToStuQue stuQuery) {
		return new SchoolRollQueryStuInfo(stuQuery).connectDB();
	}
	
	/**
	 * 删除学生信息
	 * @param stuDel
	 * @return
	 */
	public Object deleteStuInfo(MessageToStuDel stuDel) {
		return new SchoolRollDeleteStuInfo(stuDel).connectDB();
	}
}
