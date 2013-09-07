package vclient.srv;

import java.util.ArrayList;

import common.beans.Course;
import common.beans.Good;
import common.beans.ShoppingItem;
import common.beans.User;

/**
 * Ϊ�ͻ����ṩ���񣬽�����ģ�鴮������
 * ��������ֱ��ͨ����̬��������
 * @author goclis
 *
 */
public class ClientSrvHelper {
	private ClientSrvHelper() {} // ��ֹʵ����
	
	// ---------- �û�����ģ�� BEGIN -----------
	
	/**
	 * ʹ��UserManageClientSrvִ��ע�����
	 * @param user -- Ҫ��¼���û�
	 * @return �ɹ���ԭ�����أ�ʧ���򷵻�null
	 */
	public static User register(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv();
		return uMClientSrv.register(user);
	}
	
	/**
	 * ʹ��UserManageClientSrvִ�е�¼����
	 * @param user -- Ҫ��¼���û�
	 * @return �����¼�ɹ������������������ɵ�User�����򣬷���null��ʾ��¼ʧ��
	 */
	public static User login(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv();
		return uMClientSrv.login(user);
	}
	
	/**
	 * ʹ��UserManageClientSrvִ�еǳ�����
	 * @param user -- Ҫ��¼���û�
	 * @return �ǳ��ɹ�����user��ʧ�ܷ���null
	 */
	public static User logout(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv();
		return uMClientSrv.logout(user);
	}

	// ------------ �û�����ģ�� END ---------------
	
	// ------------ �̵�ģ�� BEGIN -----------------
	
	/**
	 * ʹ��StoreClientSrvִ�������Ʒ����
	 * @param good -- Ҫ��ӵ���Ʒ
	 * @return ��ӳɹ�����ԭ��Ʒ��ʧ�ܷ���null
	 */
	public static Good addNewGood(Good good) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.addNewGood(good);
	}
	
	/**
	 * ʹ��StoreClientSrvִ�а��ؼ��ֲ�ѯ��Ʒ����
	 * @param key -- �ؼ���
	 * @return ��ѯ�ɹ��򷵻���Ʒ�б�ʧ�ܷ���null
	 */
	public static ArrayList<Good> queryByKey(String key) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.queryByKey(key);
	}
	
	/**
	 * ʹ��StoreClientSrvִ�а�����ѯ��Ʒ����
	 * @param type -- ���
	 * @return ��ѯ�ɹ��򷵻���Ʒ�б�ʧ�ܷ���null
	 */
	public static ArrayList<Good> queryByType(String type) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.queryByType(type);
	}
	
	/**
	 * ʹ��StoreClientSrvִ�н��㹺�ﳵ����
	 * @param list -- Ҫ�������Ʒ����Ʒ���+���������б�
	 * @param user -- ������Ʒ���û�
	 * @return ����ɹ�����null�����򷵻س����⣨<b>ȱ��</b>������Ʒ�ı�ŵ��б�
	 * ����б�Ϊ�գ���Ϊnull˵��ͨ��ʧ�ܣ���socket==null������Ϊ��Ϊȱ��
	 */
	public static ArrayList<Integer> buyGoods(ArrayList<ShoppingItem> list, User user) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.buyGoods(list, user);
	}
	
	// ------------ �̵�ģ�� ENG -------------------
	
	// ------------ ѡ��ģ�� BEGIN -----------------
	
	/**
	 * ʹ��CourseCLientSrv�����û���ӿγ̣�ѧ��ѡ�Σ���ʦ��ӿΣ�
	 * @param courseId -- Ҫ��ӵĿγ�
	 * @param user -- ��ӿγ̵��û�
	 * @return ��ӳɹ�����courseId�����򷵻�null
	 */
	public static Integer userAddCourse(Integer courseId, User user) {
		CourseClientSrv courseClientSrv = new CourseClientSrv();
		return courseClientSrv.userAddCourse(courseId, user);
	}
	
	/**
	 * ʹ��CourseCLientSrv���в�ѯ�û��γ�
	 * @param user -- Ҫ��ѯ���û�
	 * @return ��ѯ�ɹ�����course���б�����Ϊ���б�
	 */
	public static ArrayList<Course> queryUserCourse(String userId) {
		CourseClientSrv courseClientSrv = new CourseClientSrv();
		return courseClientSrv.queryUserCourse(userId);
	}
	
	/**
	 * ʹ��CourseClientSrv��ѯ���пγ�
	 * @return
	 */
	public static ArrayList<Course> queryAllCourse() {
		CourseClientSrv courseClientSrv = new CourseClientSrv();
		return courseClientSrv.queryAllCourse();
	}
	
	// ------------ ѡ��ģ�� END -------------------
}
