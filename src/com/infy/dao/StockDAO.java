package com.infy.dao;

import java.util.List;

import com.infy.model.FiiDiiData;
import com.infy.model.MyData;
import com.infy.model.OITodayData;

public interface StockDAO {
	public void insertIntoDB();
	public List<MyData> getDataFromDB();
	public Double strikePriceCalc();
	public List<Double> parameterATMCalc();
	public List<Double> parameterPCRCalc();
	public List<Double> parameterOICalc();
	public List<Double> parameterRSCalc();
	public List<Double> parameterVIXCalc();
	public List<Double> parameterIVCalc();
	public List<Double> parameterFiiDiiCalc();
	public List<Double> parameterPremiumDecay();
	public void insertOIChangeTodayData();
	public List<OITodayData> getOIChangeTodayData();
	public List<Double> finalPrediction();
	public List<Double> directPredictFromWeb();
	public void insertFiiDiiResult();
	public List<FiiDiiData> getFiiDiiData();
}
