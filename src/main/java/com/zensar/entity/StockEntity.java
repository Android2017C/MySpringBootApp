package com.zensar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STOCKS")
public class StockEntity {
	@Id
	@GeneratedValue
	private int id;
    @Column(name="stock_name")
	private String name;
    @Column(name="stock_market")
	private String market;
    @Column(name="stock_price")
	private double price;
	
	public StockEntity(int id, String name, String market, double price) {
		super();
		this.id = id;
		this.name = name;
		this.market = market;
		this.price = price;
	}
	
	public StockEntity(String name, String market, double price) {
		super();
		this.name = name;
		this.market = market;
		this.price = price;
	}
	
	public StockEntity() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Stock [id=" + id + ", name=" + name + ", market=" + market + ", price=" + price + "]";
	}

}
