package com.zensar.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="stocks")
public class StockDocument {
	
    @Id
	private int id;
	private String name;
	private String market;
	private double price;
	public StockDocument(int id, String name, String market, double price) {
		super();
		this.id = id;
		this.name = name;
		this.market = market;
		this.price = price;
	}
	public StockDocument( String name, String market, double price) {
		super();
		this.name = name;
		this.market = market;
		this.price = price;
	}
	public StockDocument() {}
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
		return "StockDocument [id=" + id + ", name=" + name + ", market=" + market + ", price=" + price + "]";
	}
	
	

}
