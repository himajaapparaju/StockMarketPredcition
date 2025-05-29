package com.infy.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.infy.model.FiiDiiData;
import com.infy.model.MyData;
import com.infy.model.OITodayData;
import com.infy.service.StockService;
import com.infy.service.StockServiceImpl;

@RestController
@CrossOrigin
@RequestMapping(value="StockAPI")
public class StockAPI {

	private StockService service;
	private Environment environment;
	
	@RequestMapping(method=RequestMethod.GET, value="getDataFromDB")
	public ResponseEntity<Object> getDataFromDatabase(){
		List<MyData> list = new ArrayList<MyData>();
		try {
			service = new StockServiceImpl();
			list = service.getDataFromDatabase();
			for(MyData data: list) {
				data.setMessage("Data Found");
			}
			return new ResponseEntity<Object>(list,HttpStatus.OK);
		} catch (Exception exception) {
			for(MyData data: list) {
				data.setMessage(environment.getProperty(exception.getMessage()));
			}			
			return new ResponseEntity<Object>(list,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="getOIDataFromDB")
	public ResponseEntity<Object> getOIDataFromDatabase(){
		List<OITodayData> list = new ArrayList<OITodayData>();
		try {
			service = new StockServiceImpl();
			list = service.getOIDataFromDatabase();
			for(OITodayData data: list) {
				data.setMessage("Data Found");
			}
			return new ResponseEntity<Object>(list,HttpStatus.OK);
		} catch (Exception exception) {
			for(OITodayData data: list) {
				data.setMessage(environment.getProperty(exception.getMessage()));
			}			
			return new ResponseEntity<Object>(list,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="getFinalPrediction")
	public ResponseEntity<Object> getFinalPrediction(){
		List<Double> list = new ArrayList<Double>();
		try {
			service = new StockServiceImpl();
			list = service.getFinalPrediction();
			return new ResponseEntity<Object>(list,HttpStatus.OK);
		} catch (Exception exception) {			
			return new ResponseEntity<Object>(list,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="getFinalPredictionFromWeb")
	public ResponseEntity<Object> getFinalPredictionFromWeb(){
		List<Double> list = new ArrayList<Double>();
		try {
			service = new StockServiceImpl();
			list = service.getFinalPredictionFromWeb();
			return new ResponseEntity<Object>(list,HttpStatus.OK);
		} catch (Exception exception) {			
			return new ResponseEntity<Object>(list,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="getFiiDiiData")
	public ResponseEntity<Object> getFiiDiiData(){
		List<FiiDiiData> list = new ArrayList<FiiDiiData>();
		try {
			service = new StockServiceImpl();
			list = service.getFiiDiiData();
			for(FiiDiiData data: list) {
				data.setMessage("Data Found");
			}
			return new ResponseEntity<Object>(list,HttpStatus.OK);
		} catch (Exception exception) {
			for(FiiDiiData data: list) {
				data.setMessage(environment.getProperty(exception.getMessage()));
			}			
			return new ResponseEntity<Object>(list,HttpStatus.BAD_REQUEST);
		}
	}
	
}
