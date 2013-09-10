package common.util;

/**
 * 一些常用到的变量列表，常用作表头等
 * @author zhongfang
 */
public interface Variable {
	public static final String[] BOOK_VARIABLES_LIST = {"书编号","书名","责任者"
		,"类型","索书号","馆藏地","出版社","出版时间","入馆时间","单价","状态","附注"};
	
	public static final String[] BOOK_FIND_TYPE={"书编号","书名","作者","索书号","馆藏地","出版社"};
	
	public static final String[] READER_VARIABLES_LIST = {"编号","一卡通号","注册日期","生效日期","失效日期","余额","状态","规则"};
	
	public static final String[] STORE_PLACE_LIST={"中图阅览室1","中图阅览室2","中图阅览室3","中图阅览室4","外文图书阅览室","保留本阅览室"};
	
	public static final String[] RULE_VARIABLES_LIST={"规则编号","规则名称","最大借阅天数","最大续借天数","最大续借次数","保存预约天数","最多预约书本数"};
	
	public static final String[] BOOK_TYPE_LIST = {"马克思主义","哲学、宗教","社会科学总论",
		"政治、法律","军事","经济","文化、科学、教育、体育","语言、文字","文学","艺术","历史、地理","自然科学总论","数理科学与化学",
		"天文学、地球科学","生物科学","医药、卫生","农业科学","工业技术","交通运输","航空、航天","环境科学,安全科学","综合性图书"};
	
	public static final String[] BOOK_STATUS= {"在编","可借","可预约","已预约","等待预约取书","已挂失","保留本"};
	public static final String[] READER_STATUS={"注册中","未激活","正常","欠费","已挂失"};

	public static final String[] BORROW_NOW_LIST={"书编号","书名","作者","借阅日期","应还日期","续借次数","馆藏地"};
	public static final String[] BORROW_PAST_LIST={"书编号","书名","作者","借阅日期","还书日期","馆藏地"};
	public static final String[] PUNISH_VARIABLE_LIST={"原因","书编号","书名","责任者","索书号","馆藏地","产生日期","结算日期","金额","状态"};
	public static final String[] ORDER_VARIABLE_LIST={"预约号","书名","作者","索书号","馆藏地","预约日期","截止日期","状态"};
	//管理事物
	final int BOOK_MANAGE=1;
	final int READER_MANAGE=2;
	final int RULE_MANAGE=3;
	//管理类型
	final int ADD=1;
	final int MODIFY=2;
	final int DELETE=3;
}
