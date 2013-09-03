/**
 * 
 */
package common.beans;

import java.io.Serializable;

/**
 * 保存<商品编号，数量>的实体类
 * @author goclis
 *
 */
public class ShoppingItem implements Serializable {
	private Integer goodId; // 商品编号
	private Integer number; // 购买数量
	
	public ShoppingItem() {
		// TODO Auto-generated constructor stub
	}
	
	public ShoppingItem(Integer gId, Integer gNumber) {
		this.goodId = gId;
		this.number = gNumber;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (((ShoppingItem) o).getGoodId().equals(this.goodId)) {
			return true;
		} else {
			return false;
		}
	}

	// Getters and Setters
	public Integer getGoodId() {
		return goodId;
	}

	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
