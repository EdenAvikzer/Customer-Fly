package entity;

import java.time.LocalDate;
import java.util.Date;

public class Order {
	
	int OrderNum;
	Date OrderDate;
	util.PamentMethod PamentMethod;
	private boolean isCanceled;
	
	
	public Order(int orderNum, Date orderDate, util.PamentMethod pamentMethod) {
		super();
		this.OrderNum = orderNum;
		this.OrderDate = java.sql.Date.valueOf(LocalDate.now());
		this.PamentMethod = pamentMethod;
		this.setCanceled(false);
	}
	
	public Order(int orderNum, util.PamentMethod pm) {
		super();
		this.OrderDate = java.sql.Date.valueOf(LocalDate.now());
		this.OrderNum = orderNum;
		this.PamentMethod = pm;
	}

	public int getOrderNum() {
		return OrderNum;
	}
	public void setOrderNum(int orderNum) {
		OrderNum = orderNum;
	}
	public Date getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(Date orderDate) {
		OrderDate = orderDate;
	}
	public util.PamentMethod getPamentMethod() {
		return PamentMethod;
	}
	public void setPamentMethod(util.PamentMethod pamentMethod) {
		PamentMethod = pamentMethod;
	}
	

	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	@Override
	public String toString() {
		return "Order [OrderNum=" + OrderNum + ", OrderDate=" + OrderDate + ", PamentMethod=" + PamentMethod + "]";
	}
	
	

}
