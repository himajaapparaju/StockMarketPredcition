package com.infy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infy.dao.StockDAOImpl;
import com.infy.model.FiiDiiData;
import com.infy.model.MyData;
import com.infy.model.OITodayData;

@Service("stockService")
@Transactional(readOnly = true)
public class StockServiceImpl implements StockService {
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public List<MyData> getData() throws Exception{
		List<MyData> listResult = new ArrayList<MyData>();
		MyData data = new MyData();
		List<Double> atmResult = new StockDAOImpl().parameterATMCalc();
		List<Double> pcrValue = new StockDAOImpl().parameterPCRCalc();
		List<Double> oiResult = new StockDAOImpl().parameterOICalc();
		List<Double> rsResult = new StockDAOImpl().parameterRSCalc();
		if(atmResult.isEmpty()) {
			throw new Exception("No ATM Data");
		} else if(pcrValue == null) {
			throw new Exception("No PCR Data");
		} else {
			data.setAtm(atmResult);
			data.setPcr(pcrValue);
			data.setOi(oiResult);
			data.setRs(rsResult);
			listResult.add(data);
		}
		return listResult;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public List<MyData> getDataFromDatabase() {
		List<MyData> result = new StockDAOImpl().getDataFromDB();
		return result;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public List<OITodayData> getOIDataFromDatabase() {
		List<OITodayData> result = new StockDAOImpl().getOIChangeTodayData();
		return result;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public List<Double> getFinalPrediction() {
		List<Double> result = new StockDAOImpl().finalPrediction();
		return result;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public List<Double> getFinalPredictionFromWeb() {
		List<Double> result = new StockDAOImpl().directPredictFromWeb();
		return result;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public List<FiiDiiData> getFiiDiiData() {
		List<FiiDiiData> result = new StockDAOImpl().getFiiDiiData();
		return result;
	}
}
