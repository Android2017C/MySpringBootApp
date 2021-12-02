package com.zensar.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.zensar.entity.StockDocument;

public interface StockMongoRepo extends MongoRepository<StockDocument, Integer>{

List<StockDocument> findByName(String name);
	
	List<StockDocument> findByNameContaining(String name);
	List<StockDocument> findByNameIsContaining(String name);
	List<StockDocument> findByNameContains(String name);
	
	List<StockDocument> findByOrderByNameAsc();
	List<StockDocument> findByOrderByNameDesc();
	
	  @Query(value = "SELECT se from StockDocument se WHERE se.name LIKE %:name%")
	  List<StockDocument> findByNameLike(@Param("name")String stockName);
	  
		/*
		 * @Query(value="SELECT * FROM Stock ORDER BY current_value", nativeQuery =
		 * true) //sql queries List<StockDocument> sortStocksByPriceDesc();
		 */
	 
	
	List<StockDocument> findByMarket(String market);
	List<StockDocument> findByNameAndMarket(String name, String market);
	
}
