package com.zensar.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.zensar.db.Stock;
import com.zensar.exception.InvalidStockIdException;
import com.zensar.service.StockService;

import io.swagger.annotations.ApiOperation;

@RestController
public class StockController {
	
	@Autowired
	@Qualifier("MONGO_SERVICE")   // IF data from Mysql database use ===> @Qualifier("JPA_SERVICE") 
	private StockService stockService;
	
	@ExceptionHandler(value= {InvalidStockIdException.class})
	public ResponseEntity<String> handleInvalidStockIdError(RuntimeException exception,WebRequest request)
	{	
	return new ResponseEntity<String>("Local Handler Invalid Stock Id ",HttpStatus.BAD_REQUEST);
	}
	
	private static Map<Integer,Stock> stocksmap=new HashMap<Integer,Stock>();
	
	
	private static int laststockid=3;
static {
	
	stocksmap.put(1,new Stock(1,"info","NSE",20000));
	stocksmap.put(2,new Stock(2,"wipro","BSE",20000));
	stocksmap.put(3,new Stock(3,"zen","NSE",20000));
	stocksmap.put(4,new Stock(4,"hcl","BSE",20000));
}

@DeleteMapping(value = "/stock/{id}")
@ApiOperation(value="Delete Stock by id")
public boolean deleteStockById(@PathVariable("id") int stockId) {	
	return stockService.deleteStockById(stockId);
}


@DeleteMapping(value = "/stock")
@ApiOperation(value="Delete All Stock ")
public boolean deleteAllStocks() {
	return stockService.deleteAllStocks();
}


@PutMapping(value="/stock/{id}",
consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Update Stock by id")
public Stock updateStockById(@PathVariable("id") int stockId,@RequestBody Stock newStock) {
	return stockService.updateStock(stockId, newStock);
}

/*
 * @PostMapping(value="/stock", consumes=
 * {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, produces=
 * {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
 * 
 * @ApiOperation(value="Create new Stock") public Stock
 * createNewStock(@RequestBody Stock stock) {
 * 
 * laststockid++; stock.setId(laststockid); stocksmap.put(laststockid,stock);
 * return stock; }
 */


@PostMapping(value="/stock",
consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Create new Stock")
public Stock createNewStock(@RequestBody Stock stock) {
	return stockService.createNewStock(stock);
}


@GetMapping(value="/stock/{id}", 
produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Get Stock by id")
public Stock getStocksById(@PathVariable("id") int stockId){
	return stockService.getStockById(stockId);
}


@GetMapping(value="/stock", 
produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Get All Stock")
public Collection<Stock> getAllStocks(){
	return stockService.getAllStock();
}

@GetMapping(value="/stock/name/{name}", produces = {MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Get Stock by Name")
public List<Stock> getStocksByName(@PathVariable("name") String name) {
	return stockService.findByName(name);
}

@GetMapping(value="/stock/page/{startIndex}/{records}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Get Stock by Start Index and RecordsCount")
public List<Stock> getStocksByPage(@PathVariable("startIndex") int startIndex, @PathVariable("records") int records) {
	return stockService.findByPage(startIndex, records);
}
@GetMapping(value="/stock/name/like/{name}", produces = {MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Get Stock by Name Like")
public List<Stock> getStocksByNameLike(@PathVariable("name") String name) {
	return stockService.findByNameLike(name);
}

@GetMapping(value="/stock/market/{market}", produces = {MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Get Stock by Market")
public List<Stock> getStocksByMarketName(@PathVariable("market") String market) {
	return stockService.findByMarketName(market);
}

@GetMapping(value="/stock/market/{market}/name/{name}", produces = {MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Get Stock by Market And Name")
public List<Stock> getStocksByNameAndMarketName(@PathVariable("market") String marketname, @PathVariable("name") String name) {
	return stockService.findByNameAndMarket(name, marketname);
}

@GetMapping(value="/stock/name/sort/{sortType}", produces = {MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@ApiOperation(value="Get Stock Sort by Passing ASE Or DESE")
public List<Stock> getStocksOrderByName(@PathVariable("sortType") String sortType) {
	return stockService.findByOrderByName(sortType);
}

}
