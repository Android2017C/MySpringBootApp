package com.zensar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zensar.db.Stock;
import com.zensar.entity.StockDocument;
import com.zensar.repo.StockMongoRepo;

@Service(value = "MONGO_SERVICE")
public class StockServiceMongoImpl implements StockService {
	@Autowired
	private StockMongoRepo stockMongoRepo; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	private StockDocument getStockEntityFromDTO(Stock stock) {
		//return new StockDocument(stock.getName(),stock.getMarket(),stock.getPrice());
		//stock.getmarketname()-> StockDocument.setmarket()
		TypeMap<Stock,StockDocument> typeMap=this.modelMapper.typeMap(Stock.class, StockDocument.class);
				typeMap.addMappings(mapper -> {
					mapper.map(source -> source.getMarketName(),StockDocument::setMarket);
				});
		
				StockDocument stockDocument=this.modelMapper.map(stock, StockDocument.class);
		return stockDocument;
		
	}
	
private Stock getStockDTOFromEntity(StockDocument stockDocument) {
		//return new Stock(stockEntity.getId(),stockEntity.getName(),stockEntity.getMarket(),stockEntity.getPrice());
	// stockentity.setmarket() -> stock.getmarket()
	TypeMap<StockDocument,Stock> typeMap=this.modelMapper.typeMap(StockDocument.class, Stock.class);
	typeMap.addMappings(mapper -> {
		mapper.map(source -> source.getMarket(),Stock::setMarketName);
	});
	
	Stock stockDTO=this.modelMapper.map(stockDocument, Stock.class);
	return stockDTO;
	}
	

	@Override
	public Stock createNewStock(Stock stock) {
		// TODO Auto-generated method stub
		StockDocument stockDocument=getStockEntityFromDTO(stock);
		stockDocument=stockMongoRepo.save(stockDocument);
		return getStockDTOFromEntity(stockDocument);
	}

	@Override
	public boolean deleteAllStocks() {
		stockMongoRepo.deleteAll();
		return true;
	}

	@Override
	public boolean deleteStockById(int stockId) {
		stockMongoRepo.deleteById(stockId);
		return true;
	}

	@Override
	public Stock updateStock(int stockId, Stock stock) {
		Optional<StockDocument> opStockDocument=stockMongoRepo.findById(stockId);
            if(opStockDocument.isPresent()){
            StockDocument stockDocument=opStockDocument.get();
			stockDocument.setName(stock.getName());
			stockDocument.setMarket(stock.getMarketName());
			stockDocument.setPrice(stock.getPrice());
			stockMongoRepo.save(stockDocument);
		}
		return null;
	}

	@Override
	public Stock getStockById(int stockId) {
		Optional<StockDocument> opStockDocument=stockMongoRepo.findById(stockId);
		if(opStockDocument.isPresent()){
			StockDocument stockDocument=opStockDocument.get();
			return getStockDTOFromEntity(stockDocument);
		}
		return null;
	}

	@Override
	public List<Stock> getAllStock() {
		List<StockDocument> stockDocumentList=stockMongoRepo.findAll();
		return getStockDTOListFromDocumentList(stockDocumentList);
	}
	
	private List<Stock> getStockDTOListFromDocumentList(List<StockDocument> stockDocumentList){
		List<Stock> stockDTOList=new ArrayList<Stock>();
		for(StockDocument stockDocument : stockDocumentList){
		stockDTOList.add(getStockDTOFromEntity(stockDocument));
		}
       return stockDTOList;
	}

	@Override
	public List<Stock> findByName(String name) {
		List<StockDocument> stockDocumentList = stockMongoRepo.findByName(name);
		return getStockDTOListFromDocumentList(stockDocumentList);
	}

	@Override
	public List<Stock> findByPage(int startIndex, int records) {
		Pageable pageWithFewRecords = PageRequest.of(startIndex, records);
		Page<StockDocument> stockDocumentPage = stockMongoRepo.findAll(pageWithFewRecords);
		List<StockDocument> stockDocumentList = stockDocumentPage.getContent();
		return getStockDTOListFromDocumentList(stockDocumentList);
	}

	@Override
	public List<Stock> findByMarketName(String market) {
		List<StockDocument> stockDocumentList = stockMongoRepo.findByMarket(market);
		return getStockDTOListFromDocumentList(stockDocumentList);
	}

	

	
	  @Override public List<Stock> findByNameLike(String name) {
	  List<StockDocument> stockDocumentList = stockMongoRepo.findByNameLike(name);
	  return getStockDTOListFromDocumentList(stockDocumentList); }
	

	@Override
	public List<Stock> findByOrderByName(String sortType) {
		List<StockDocument> stockDocumentList = null;
		if("ASC".equalsIgnoreCase(sortType)) {
			stockDocumentList = stockMongoRepo.findByOrderByNameAsc();
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
			stockDocumentList = stockMongoRepo.findByOrderByNameDesc();
		}
		return getStockDTOListFromDocumentList(stockDocumentList);
	}

	@Override
	public List<Stock> findByNameAndMarket(String name, String market) {
		List<StockDocument> stockDocumentList = stockMongoRepo.findByNameAndMarket(name, market);
		return getStockDTOListFromDocumentList(stockDocumentList);
	}




}
