package com.zensar.db;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Stock Model")
public class Stock {
@ApiModelProperty("Unique identifier of the Stock")
private int id;
@ApiModelProperty("Name of the Stock")
private String name;
@ApiModelProperty("Market name where stock is registered")
private String marketName;
@ApiModelProperty("Current price of the Stock")
private double price;
public Stock(int id, String name, String marketName, double price) {
	super();
	this.id = id;
	this.name = name;
	this.marketName = marketName;
	this.price = price;
}
public Stock() {
	
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
public String getMarketName() {
	return marketName;
}
public void setMarketName(String marketName) {
	this.marketName = marketName;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
@Override
public String toString() {
	return "Stock [id=" + id + ", name=" + name + ", market=" + marketName + ", price=" + price + "]";
}

}
