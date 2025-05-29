package com.infy.service;

import java.util.List;

import com.infy.model.FiiDiiData;
import com.infy.model.MyData;
import com.infy.model.OITodayData;

public interface StockService {
	public List<MyData> getData() throws Exception;
	public List<MyData> getDataFromDatabase();
	public List<OITodayData> getOIDataFromDatabase();
	public List<Double> getFinalPrediction();
	public List<Double> getFinalPredictionFromWeb();
	public List<FiiDiiData> getFiiDiiData();
}
