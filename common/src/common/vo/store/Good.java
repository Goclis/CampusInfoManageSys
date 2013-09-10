/**
 * 
 */
package common.vo.store;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 商品实体类
 * @author goclis
 *
 */
public class Good implements Serializable {
	private Integer id; // 商品编号
	private String name; // 商品名
	private Double price; // 商品价格
	private Integer number; // 商品数量
	private String type; // 商品类型
	
	// 保存商品所有的类型
	// TODO: 需要即使更新，确定更新的时机，此处硬编码
	// public static ArrayList<String> GOODTYPES = new ArrayList<String>();
	public final static String[] GOODTYPES = {
		"食品", "饮料", "水果", "学习用品", "生活用品", "其他"};
	
	public Good() {
		this.id = 0;
		this.name = null;
		this.price = 0.0;
		this.number = 0;
	}

	public Good(String id, String name, String price, String number,
			String type) {
		this.id = Integer.valueOf(id);
		this.name = name;
		this.price = Double.valueOf(price);
		this.number = Integer.valueOf(number);
		this.type = type;
	}

	public Good(String gName, double gPrice, int gNumber, String gType) {
		this.name = gName;
		this.price = gPrice;
		this.number = gNumber;
		this.type = gType;
	}

	// Setters and Getters
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
