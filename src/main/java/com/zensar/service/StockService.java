package com.zensar.service;

import java.util.List;

import com.zensar.db.Stock;

public interface StockService {
public Stock createNewStock(Stock stock);
public boolean deleteAllStocks();
public boolean deleteStockById(int stockId);
public Stock updateStock(int stockId, Stock stock);
public Stock getStockById(int stockId);
public List<Stock> getAllStock();

List<Stock> findByName(String name);
List<Stock> findByPage(int startIndex, int records);
List<Stock> findByMarketName(String market);
List<Stock> findByNameAndMarket(String name, String market);
List<Stock> findByNameLike(String name);	
List<Stock> findByOrderByName(String sortType);

}
