package com.zensar.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zensar.entity.StockEntity;

public interface StockRepo extends JpaRepository<StockEntity, Integer>{
	List<StockEntity> findByName(String name);
	
	List<StockEntity> findByNameContaining(String name);
	List<StockEntity> findByNameIsContaining(String name);
	List<StockEntity> findByNameContains(String name);
	
	List<StockEntity> findByOrderByNameAsc();
	List<StockEntity> findByOrderByNameDesc();
	@Query(value = "SELECT se from StockEntity se WHERE se.name LIKE %:name%")
	List<StockEntity> findByNameLike(@Param("name")String stockName);
	
	@Query(value="SELECT * FROM Stock ORDER BY current_value", nativeQuery = true) //sql queries
	List<StockEntity> sortStocksByPriceDesc();
	
	List<StockEntity> findByMarket(String market);
	List<StockEntity> findByNameAndMarket(String name, String market);
}
