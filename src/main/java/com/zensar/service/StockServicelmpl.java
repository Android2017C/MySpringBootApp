package com.zensar.service;

import java.lang.StackWalker.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.db.Stock;
import com.zensar.entity.StockEntity;
import com.zensar.repo.StockRepo;

@Service(value = "JPA_SERVICE")
public class StockServicelmpl implements StockService {
	@Autowired
	private StockRepo stockRepo; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	private StockEntity getStockEntityFromDTO(Stock stock) {
		//return new StockEntity(stock.getName(),stock.getMarket(),stock.getPrice());
		//stock.getmarketname()-> stockentity.setmarket()
		TypeMap<Stock,StockEntity> typeMap=this.modelMapper.typeMap(Stock.class, StockEntity.class);
				typeMap.addMappings(mapper -> {
					mapper.map(source -> source.getMarketName(),StockEntity::setMarket);
				});
		
		StockEntity stockEntity=this.modelMapper.map(stock, StockEntity.class);
		return stockEntity;
		
	}
	
private Stock getStockDTOFromEntity(StockEntity stockEntity) {
		//return new Stock(stockEntity.getId(),stockEntity.getName(),stockEntity.getMarket(),stockEntity.getPrice());
	// stockentity.setmarket() -> stock.getmarket()
	TypeMap<StockEntity,Stock> typeMap=this.modelMapper.typeMap(StockEntity.class, Stock.class);
	typeMap.addMappings(mapper -> {
		mapper.map(source -> source.getMarket(),Stock::setMarketName);
	});
	
	Stock stockDTO=this.modelMapper.map(stockEntity, Stock.class);
	return stockDTO;
	}
	

	@Override
	public Stock createNewStock(Stock stock) {
		// TODO Auto-generated method stub
		StockEntity stockEntity=getStockEntityFromDTO(stock);
		stockEntity=stockRepo.save(stockEntity);
		return getStockDTOFromEntity(stockEntity);
	}

	@Override
	public boolean deleteAllStocks() {
		stockRepo.deleteAll();
		return true;
	}

	@Override
	public boolean deleteStockById(int stockId) {
		stockRepo.deleteById(stockId);
		return true;
	}

	@Override
	public Stock updateStock(int stockId, Stock stock) {
		Optional<StockEntity> opstockentity=stockRepo.findById(stockId);
            if(opstockentity.isPresent()){
			StockEntity stockEntity=opstockentity.get();
			stockEntity.setName(stock.getName());
			stockEntity.setMarket(stock.getMarketName());
			stockEntity.setPrice(stock.getPrice());
			stockRepo.save(stockEntity);
		}
		return null;
	}

	@Override
	public Stock getStockById(int stockId) {
		Optional<StockEntity> opstockentity=stockRepo.findById(stockId);
		if(opstockentity.isPresent()){
			StockEntity stockEntity=opstockentity.get();
			return getStockDTOFromEntity(stockEntity);
		}
		return null;
	}

	@Override
	public List<Stock> getAllStock() {
		List<StockEntity> stockEntityList=stockRepo.findAll();
		return getStockDTOListFromEntityList(stockEntityList);
	}
	
	private List<Stock> getStockDTOListFromEntityList(List<StockEntity> stockEntityList){
		List<Stock> stockDTOList=new ArrayList<Stock>();
		for(StockEntity stockEntity : stockEntityList){
		stockDTOList.add(getStockDTOFromEntity(stockEntity));
		}
       return stockDTOList;
	}

	@Override
	public List<Stock> findByName(String name) {
		List<StockEntity> stockEntityList = stockRepo.findByName(name);
		return getStockDTOListFromEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByPage(int startIndex, int records) {
		Pageable pageWithFewRecords = PageRequest.of(startIndex, records);
		Page<StockEntity> stockEntityPage = stockRepo.findAll(pageWithFewRecords);
		List<StockEntity> stockEntityList = stockEntityPage.getContent();
		return getStockDTOListFromEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByMarketName(String market) {
		List<StockEntity> stockEntityList = stockRepo.findByMarket(market);
		return getStockDTOListFromEntityList(stockEntityList);
	}

	

	@Override
	public List<Stock> findByNameLike(String name) {
		List<StockEntity> stockEntityList = stockRepo.findByNameLike(name);
		return getStockDTOListFromEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByOrderByName(String sortType) {
		List<StockEntity> stockEntityList = null;
		if("ASC".equalsIgnoreCase(sortType)) {
			stockEntityList = stockRepo.findByOrderByNameAsc();
			/*
			List<Sort.Order> sortOrders = new ArrayList<Sort.Order>();
			Sort.Order order_1 = new Sort.Order(Sort.Direction.ASC, "name");
			Sort.Order order_2 = new Sort.Order(Sort.Direction.DESC, "market");
			sortOrders.add(order_1);
			sortOrders.add(order_2);
			stockEntityList = stockRepository.findAll(Sort.by(sortOrders));
			stockEntityList = stockRepository.findAll(Sort.by(new Sort.Order(Sort.Direction.DESC, "price")));
			*/
		}
		if("DESC".equalsIgnoreCase(sortType)) {
			stockEntityList = stockRepo.findByOrderByNameDesc();
		}
		return getStockDTOListFromEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByNameAndMarket(String name, String market) {
		List<StockEntity> stockEntityList = stockRepo.findByNameAndMarket(name, market);
		return getStockDTOListFromEntityList(stockEntityList);
	}



}
