package com.infy.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.infy.model.FiiDiiData;
import com.infy.model.MyData;
import com.infy.model.OITodayData;

@Repository("dao")
public class StockDAOImpl implements StockDAO {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public StockDAOImpl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
			//con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/mystockdb","myprojectdb","myprojectdb");
			//con = DriverManager.getConnection("jdbc:mysql://localhost/id9271023_test","id9271023_mounika","Mounika@42");
			//con = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12286575","sql12286575","K2EqzuSwML");
			st = con.createStatement();
		} catch(Exception exception) {
			System.out.println("Error Occured: "+exception.getMessage());
		}
	}
	
	@Override
	public void insertIntoDB() {
		List<Double> atmResult = this.parameterATMCalc();
		List<Double> oiResult = this.parameterOICalc();
		List<Double> rsResult = this.parameterRSCalc();
		List<Double> ivResult = this.parameterIVCalc();
		List<Double> decayResult = this.parameterPremiumDecay();
		List<Double> pcr = this.parameterPCRCalc();
		List<Double> vix = this.parameterVIXCalc();
		Double sp = this.strikePriceCalc();
		try {
			String query = "INSERT INTO finalupdatedtest (MAXOICALLSIDE,MAXOIPUTSIDE,CALLSOICOUNTFORPREDICTION,PUTSOICOUNTFORPREDICTION,CALLSIDEDECAYAVG,PUTSIDEDECAYAVG,PCR,CALLSIDEIV,PUTSIDEIV,CALLSIDELTPFORATM,PUTSIDELTPFORATM,VIX,RESISTANCEVALUE,SUPPORTVALUE,OIRESULT,PDRESULT,PCRRESULT,ATMRESULT,VIXRESULT,STRIKEPRICE) "
					+ "VALUES ("+ oiResult.get(0) +","+ oiResult.get(1) +","+ oiResult.get(2) +","+ oiResult.get(3) +","
					+ decayResult.get(0) +","+ decayResult.get(1) +","
					+ pcr.get(0) +"," 
					+ ivResult.get(0) +","+ ivResult.get(1) +","
					+ atmResult.get(0) +","+ atmResult.get(1) +","
					+ vix.get(0) +","
					+ rsResult.get(0) +","+ rsResult.get(1) +","
					+ oiResult.get(4) +","+ decayResult.get(2) +","
					+ pcr.get(1) +","+ atmResult.get(2) +","
					+ vix.get(1) +","
					+ sp +")";
			st.executeUpdate(query);			
		} catch(Exception exception) {
			System.out.println("Error: " + exception.getMessage());
		}
	}

	@Override
	public List<MyData> getDataFromDB() {
		List<MyData> listResult = new ArrayList<MyData>();		
		try {
			String get = "select * from finalupdatedtest";
			rs = st.executeQuery(get);
			while(rs.next()) {
				List<Double> atmResult = new ArrayList<Double>();
				List<Double> oiResult = new ArrayList<Double>();
				List<Double> rsResult = new ArrayList<Double>();
				List<Double> ivResult = new ArrayList<Double>();
				List<Double> pdResult = new ArrayList<Double>();
				List<Double> pcrResult = new ArrayList<Double>();
				List<Double> vixResult = new ArrayList<Double>();	
				List<Double> spResult = new ArrayList<Double>();
				MyData data = new MyData();
				Double callsatm = rs.getDouble("CALLSIDELTPFORATM");
				Double putsatm = rs.getDouble("PUTSIDELTPFORATM");
				Double pcr = rs.getDouble("PCR");
				Double callsoi = rs.getDouble("MAXOICALLSIDE");
				Double putsoi = rs.getDouble("MAXOIPUTSIDE");
				Double callsoicount = rs.getDouble("CALLSOICOUNTFORPREDICTION");
				Double putsoicount = rs.getDouble("PUTSOICOUNTFORPREDICTION");
				Double callssp = rs.getDouble("RESISTANCEVALUE");
				Double putssp = rs.getDouble("SUPPORTVALUE");
				Double vix = rs.getDouble("VIX");
				Double callsiv = rs.getDouble("CALLSIDEIV");
				Double putsiv = rs.getDouble("PUTSIDEIV");
				Double callAvgDecay = rs.getDouble("CALLSIDEDECAYAVG");
				Double putAvgDecay = rs.getDouble("PUTSIDEDECAYAVG");
				Double oiPrediction = rs.getDouble("OIRESULT");
				Double pdPrediction = rs.getDouble("PDRESULT");
				Double pcrPrediction = rs.getDouble("PCRRESULT");
				Double atmPrediction = rs.getDouble("ATMRESULT");
				Double vixPrediction = rs.getDouble("VIXRESULT");
				Double sp = rs.getDouble("STRIKEPRICE");
				atmResult.add(callsatm);
				atmResult.add(putsatm);
				atmResult.add(atmPrediction);
				oiResult.add(callsoi);
				oiResult.add(putsoi);
				oiResult.add(callsoicount);
				oiResult.add(putsoicount);
				oiResult.add(oiPrediction);
				rsResult.add(callssp);
				rsResult.add(putssp);
				ivResult.add(callsiv);
				ivResult.add(putsiv);
				pdResult.add(callAvgDecay);
				pdResult.add(putAvgDecay);
				pdResult.add(pdPrediction);
				pcrResult.add(pcr);
				pcrResult.add(pcrPrediction);
				vixResult.add(vix);
				vixResult.add(vixPrediction);
				spResult.add(sp);
				data.setAtm(atmResult);
				data.setPcr(pcrResult);
				data.setOi(oiResult);
				data.setRs(rsResult);
				data.setVix(vixResult);
				data.setIv(ivResult);
				data.setPremiumDecay(pdResult);
				data.setSp(spResult);
				listResult.add(data);
			}
		} catch(Exception exception) {
			System.out.println("Error: " + exception.getMessage());
		}
		return listResult;
	}
	
	@Override
	public Double strikePriceCalc() {
		String strike="";
		try
        {
            FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/StockData(MainDemo).xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(2);
            String data = sheet.getRow(0).getCell(1).getStringCellValue();
            strike = data.substring(24,32);
            workbook.close();
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		Double num = Double.parseDouble(strike);
		int diff=0;
		double strikePrice;
		float temp = Math.round(num);
		strikePrice = (int) temp;
//		diff = (int) temp % 50;
//		if(diff <= 25)
//			strikePrice = strikePrice - diff;
//		else 
//			strikePrice = strikePrice +(50-diff);
		diff = (int) temp % 100;
		if(diff <= 50)
			strikePrice = strikePrice - diff;
		else 
			strikePrice = strikePrice +(100-diff);
		return strikePrice;
	}

	@Override
	public List<Double> directPredictFromWeb() {
		Double change1 = 0d, change2 = 0d;
		List<Double> result = new ArrayList<Double>();
		try
        {
            FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/StockData(MainDemo).xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(3);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
            	Row row = rowIterator.next();
                Cell cell = row.getCell(0);
                if(cell.getStringCellValue().equals("NIFTY 50")) {
                    change1 = row.getCell(2).getNumericCellValue();
                    change2 = row.getCell(3).getNumericCellValue();
                    break;
                }                    
            }
            result.add(change1);
            result.add(change2);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
		return result;
	}
	
	@Override
	public List<Double> parameterATMCalc() {	
		Double strikePrice = this.strikePriceCalc();
		Double callLtp=0d,putLtp=0d,callPutDiff=0d, prediction = 0d;
		List<Double> result = new ArrayList<Double>();
		int rowNo = 0;
		try
        {
            FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/StockData(MainDemo).xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
            	Row row = rowIterator.next();
                Cell cell = row.getCell(11);
                if(cell.getNumericCellValue()==strikePrice) {
                    rowNo = row.getRowNum(); 
                    break;
                }                    
            }
            callLtp = sheet.getRow(rowNo).getCell(5).getNumericCellValue();
            putLtp = sheet.getRow(rowNo).getCell(17).getNumericCellValue();
            if(callLtp > putLtp) {
				callPutDiff = callLtp - putLtp;
				if(callPutDiff > 10) {
					prediction = 1d;
					//System.out.println("Market is Bullish!!!");
				} else {
					prediction = 0d;
					//System.out.println("Market is Moving Sideways!!!");
				}
			}
			else if(callLtp < putLtp) {
				callPutDiff = putLtp - callLtp;
				if(callPutDiff > 10) {
					prediction = -1d;
					//System.out.println("Market is Bearish!!!");
				}
				else {
					prediction = 0d;
					//System.out.println("Market is Moving Sideways!!!");
				}
			}
            result.add(callLtp);
            result.add(putLtp);
            result.add(prediction);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		return result;
		
	}

	@Override
	public List<Double> parameterPCRCalc() {
		Double ratio = 0d,totalCall = 0d, totalPut = 0d, prediction = 0d;
		List<Double> result = new ArrayList<Double>();
        try {
        	FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/DBImport.xlsx"));
        	XSSFWorkbook workbook = new XSSFWorkbook(file);
        	XSSFSheet sheet = workbook.getSheetAt(0);
        	Iterator<Row> rowIterator = sheet.iterator();
        	while (rowIterator.hasNext())
            {
            	Row row = rowIterator.next();
                Cell cell = row.getCell(1);
                Cell cell1 = row.getCell(21);
                Cell c = row.getCell(2);
                try {
                	if(c.getNumericCellValue() >= 0 || c.getNumericCellValue() < 0) {
	                	totalCall = totalCall + cell.getNumericCellValue();
	                	totalPut = totalPut + cell1.getNumericCellValue();
                	}
                } catch(Exception e) {
                	break;
                }                   
            }
            ratio=totalPut/totalCall;
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	e.printStackTrace();
		}
	    if(ratio > 0.75 && ratio < 1.5) {
	    	prediction = 1d;
	    	//System.out.println("Market is Bullish");
	    }
	    else {
	    	prediction = -1d;
	    	//System.out.println("Market is Bearish");
	    }
	    result.add(ratio);
	    result.add(prediction);
	    return result;
	}
	
	@Override
	public List<Double> parameterOICalc() {
		Double strikePrice = this.strikePriceCalc();
		Double callsoi=0d,callsnet=0d,putsoi=0d,putsnet=0d, prediction = 0d;
		int rowNo = 0;
	    Double count1=0d,count2=0d;
	    List<Double> result = new ArrayList<Double>();
	    try {
	    	FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/DBImport.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
		    XSSFSheet sheet = workbook.getSheetAt(0);
		    Iterator<Row> rowIterator = sheet.iterator();
		    while (rowIterator.hasNext()) {
		    	Row row = rowIterator.next();
		        Cell cell = row.getCell(11);
		        if(cell.getNumericCellValue()==strikePrice) {
		        	rowNo = row.getRowNum(); 
		            break;
		        }                    
		    }
		    callsoi = sheet.getRow(rowNo).getCell(2).getNumericCellValue();
		    callsnet = sheet.getRow(rowNo).getCell(6).getNumericCellValue();
		    putsoi= sheet.getRow(rowNo).getCell(20).getNumericCellValue();
		    putsnet=sheet.getRow(rowNo).getCell(16).getNumericCellValue();
		    //Calls.......................!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		    if(callsoi > 0 && callsnet>0) {
		        count1+=1;
		    } else if(callsoi>0 && callsnet<0 ) {
		        count2+=1;
	        } else if(callsoi<0 && callsnet>0){
		        count1+=1;
		    } else if(callsoi<0 && callsnet<0) {
		        count2+=1;
		    }
		    //puts.......................................!!!!!!   
		    if(putsoi>0 && putsnet>0) {
		         count2+=1; 
		    } else if(putsoi>0 && putsnet<0) {
		         count1+=1;
		    } else if(putsoi<0 && putsnet>0) {
		         count2+=1;
		    } else if(putsoi<0 && putsnet<0) {
		         count1+=1;
		    }
		    //..........................................................
		    if(count1>count2) {
		    	prediction = 1d;
		        //System.out.println("Market is Bullish!!!!!!!!!!!!!");
		    }
		    else if(count1<count2) {
		    	prediction = -1d;
		        //System.out.println("Market is Bearish!!!!!!!!!!!!!");
		    }
		    else {
		    	prediction = 0d;
		        //System.out.println("Market is Moving Sideways!!!");
		    }
		    result.add(callsoi);
		    result.add(putsoi);
		    result.add(count1);
		    result.add(count2);
		    result.add(prediction);
		    workbook.close();
		    file.close();
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
	    return result;
	}
	
	@Override
	public List<Double> parameterRSCalc() {
		List<Double> result = new ArrayList<Double>();
		Double strikePrice = this.strikePriceCalc();
		Double callSP = 0d, putSP = 0d;
		Double maxCallOI = 0d, maxPutOI = 0d;
		int rowNo = 0;
        try {
        	FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/DBImport.xlsx"));
        	XSSFWorkbook workbook = new XSSFWorkbook(file);
        	XSSFSheet sheet = workbook.getSheetAt(0);
        	Iterator<Row> rowIterator = sheet.iterator();
 		    while (rowIterator.hasNext()) {
 		    	Row row = rowIterator.next();
 		        Cell cell = row.getCell(11);
 		        if(cell.getNumericCellValue()==strikePrice) {
 		        	rowNo = (int) row.getCell(0).getNumericCellValue(); 
 		            break;
 		        }                    
 		    }
 		    for(int i = rowNo + 1; i < sheet.getLastRowNum(); i++) {
 		    	Cell cell = sheet.getRow(i).getCell(1);
 		    	Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(c.getNumericCellValue() >= 0 || c.getNumericCellValue() < 0) {               		
                		if(cell.getNumericCellValue() >= maxCallOI) {
                			maxCallOI = cell.getNumericCellValue();
                			callSP = sheet.getRow(i).getCell(11).getNumericCellValue();
                		}
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
 		   for(int i = 0; i < rowNo; i++) {
 			   Cell c = sheet.getRow(i).getCell(2);
		       Cell cell = sheet.getRow(i).getCell(21);
               try {
               	if(c.getNumericCellValue() >= 0 || c.getNumericCellValue() < 0) {               		
               		if(cell.getNumericCellValue() >= maxPutOI) {
            			maxPutOI = cell.getNumericCellValue();
            			putSP = sheet.getRow(i).getCell(11).getNumericCellValue();
            		}
               	}
               } catch(Exception e) {
               	break;
               } 
		    }
 		    result.add(callSP);
 		    result.add(putSP);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
		}
        return result;
	}
	
	@Override
	public List<Double> parameterVIXCalc() {
		Double vix = 0d, prediction = 0d;
		List<Double> result = new ArrayList<Double>();
		int rowNo = 0;
        try {
        	FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/VIX.xlsx"));
        	XSSFWorkbook workbook = new XSSFWorkbook(file);
        	XSSFSheet sheet = workbook.getSheetAt(0);
        	Iterator<Row> rowIterator = sheet.iterator();
        	while (rowIterator.hasNext())
            {
            	Row row = rowIterator.next();
                Cell cell = row.getCell(0);
                if(cell.getStringCellValue().equals("INDIA VIX")) {
                	rowNo = row.getRowNum();
                	break;
                }
                	
            }
        	vix = sheet.getRow(rowNo+1).getCell(0).getNumericCellValue();
        	if (vix <= 15) {
        		prediction = 1d;
        		//System.out.println("Market is Bullish!!!");
        	} else if(vix == 16) {
        		prediction = 0d;
        		//System.out.println("Market Moving Sideways!!!");
        	} else {
        		prediction = -1d;
        		//System.out.println("Market is Bearish!!!");
        	}
            result.add(vix);
            result.add(prediction);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	e.printStackTrace();
		}
        return result;
	}

	@Override
	public List<Double> parameterIVCalc() {
		Double strikePrice = this.strikePriceCalc();
		Double callsIV = 0d, putsIV = 0d;
		List<Double> result = new ArrayList<Double>();
		int rowNo = 0;
		try
        {
            FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/DBImport.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
            	Row row = rowIterator.next();
                Cell cell = row.getCell(11);
                if(cell.getNumericCellValue()==strikePrice) {
                    rowNo = row.getRowNum(); 
                    break;
                }                    
            }
            callsIV = sheet.getRow(rowNo).getCell(4).getNumericCellValue();
            putsIV = sheet.getRow(rowNo).getCell(18).getNumericCellValue();
            result.add(callsIV);
            result.add(putsIV);
            workbook.close();
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return result;
	}

	@Override
	public List<Double> parameterFiiDiiCalc() {
		Double diiTotalLongContracts = 0d, diiTotalShortContracts = 0d,  fiiTotalLongContracts = 0d, fiiTotalShortContracts = 0d;
		Double diiDiff = 0d, fiiDiff = 0d, diiFiiDiff = 0d;
		List<Double> result = new ArrayList<Double>();
		try
        {
            FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/april12fiidii.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
            	Row row = rowIterator.next();
                Cell cell = row.getCell(0);
                if(cell.getStringCellValue().equals("DII")) {
                	diiTotalLongContracts = row.getCell(13).getNumericCellValue();
                	diiTotalShortContracts = row.getCell(14).getNumericCellValue();
                }
                if(cell.getStringCellValue().equals("FII")) {
                	fiiTotalLongContracts = row.getCell(13).getNumericCellValue();
                	fiiTotalShortContracts = row.getCell(14).getNumericCellValue();
                }
            }
            diiDiff = diiTotalLongContracts - diiTotalShortContracts;
            fiiDiff = fiiTotalLongContracts - fiiTotalShortContracts;
            if(diiDiff > 0 && fiiDiff > 0) {
            	System.out.println("Bullish!!!");
            } else if(diiDiff < 0 && fiiDiff < 0) {
            	System.out.println("Bearish!!!");
            } else {
            	diiFiiDiff = diiDiff - fiiDiff;
            	if(diiFiiDiff > 0) {
            		System.out.println("Slightly Bullish!!!");
            	} else {
            		System.out.println("Slightly Bearish!!!");
            	}
            }
            result.add(fiiDiff);
            result.add(diiDiff);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}
	
	@Override
	public void insertOIChangeTodayData() {
		Double strikePrice = this.strikePriceCalc();
        Double callChangeOIValue=0d,putChangeOIValue=0d,strike=0d;
        int rowNo = 0;
        try
        {
		      FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/StockData(MainDemo).xlsx"));
		      XSSFWorkbook workbook = new XSSFWorkbook(file);
		      XSSFSheet sheet = workbook.getSheetAt(0);
		      Iterator<Row> rowIterator = sheet.iterator();
		      while (rowIterator.hasNext())
		      {
		        Row row = rowIterator.next();
		          Cell cell = row.getCell(11);
		          if(cell.getNumericCellValue()==strikePrice) {
		              rowNo = row.getRowNum(); 
		              break;
		          }                    
		      }
		      for (int i = rowNo-3;i<=rowNo+3;i++){
		        callChangeOIValue=sheet.getRow(i).getCell(2).getNumericCellValue();
		          putChangeOIValue = sheet.getRow(i).getCell(20).getNumericCellValue();
		          strike=sheet.getRow(i).getCell(11).getNumericCellValue();
		          String query = "INSERT INTO oitodaydata (STRIKEPRICE,CALLOICHANGEVALUE,PUTOICHANGEVALUE) "
		                             + "VALUES ("+ strike +","+ callChangeOIValue +","+ putChangeOIValue +")";
		          st.executeUpdate(query);                   		          
		      }		      		        
		 }catch(Exception exception){
		        System.out.println("Error: " + exception.getMessage());
		 }
	}

	@Override
	public List<OITodayData> getOIChangeTodayData() {
		List<OITodayData> listResult = new ArrayList<OITodayData>();		
		try {
			String get = "select * from oitodaydata";
			rs = st.executeQuery(get);
			while(rs.next()) {
				OITodayData today = new OITodayData();
				Double strikePrice = rs.getDouble("STRIKEPRICE");
				Double callOIChangeValue = rs.getDouble("CALLOICHANGEVALUE");
				Double putOIChangeValue = rs.getDouble("PUTOICHANGEVALUE");
				today.setStrikePrice(strikePrice);
				today.setCallOIChangeValue(callOIChangeValue);
				today.setPutOIChangeValue(putOIChangeValue);
				listResult.add(today);
			}
		} catch(Exception exception) {
			System.out.println("Error: " + exception.getMessage());
		}
		return listResult;
	}
	
	@Override
	public List<Double> finalPrediction() {
		List<Integer> rowNos = new ArrayList<Integer>();
		List<Double> finalResult = new ArrayList<Double>();
        Double strikePrice = this.strikePriceCalc();
		List<Double> presentPCRList = this.parameterPCRCalc();
		Double presentPCR = presentPCRList.get(0);
		Double count1 = 0d, count2=0d,callputscount=0d,callscount1=0d,putscount1=0d,callscount2=0d,putscount2=0d;        
    	Double callITMMax1 = 0d, callITMMax2 = 0d, callOTMMax1 = 0d, callOTMMax2 = 0d;
    	Double putITMMax1 = 0d, putITMMax2 = 0d, putOTMMax1 = 0d, putOTMMax2 = 0d;
    	Integer putITMMax1Row = 0, putITMMax2Row = 0, putOTMMax1Row = 0, putOTMMax2Row = 0;
    	Integer rowNo = 0, callITMMax1Row = 0, callITMMax2Row = 0, callOTMMax1Row = 0, callOTMMax2Row = 0;
		try
        {
            FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/DBImport.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
            	Row row = rowIterator.next();
                Cell cell = row.getCell(11);
                if(cell.getNumericCellValue()==strikePrice) {
                    rowNo = row.getRowNum(); 
                    break;
                }                    
            }
            for(int i = 0; i < rowNo; i++) {
  			    Cell cell = sheet.getRow(i).getCell(1);
  			    Cell cell1 = sheet.getRow(i).getCell(21);
  			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell.getNumericCellValue() > callITMMax1 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		callITMMax1 = cell.getNumericCellValue();
                		callITMMax1Row = i;
                	}
                	if(cell1.getNumericCellValue() > putOTMMax1 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		putOTMMax1 = cell1.getNumericCellValue();
                		putOTMMax1Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = 0; i < rowNo; i++) {
  			    Cell cell = sheet.getRow(i).getCell(1);
  			    Cell cell1 = sheet.getRow(i).getCell(21);
  			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell.getNumericCellValue() < callITMMax1 && cell.getNumericCellValue() > callITMMax2 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		callITMMax2 = cell.getNumericCellValue();
                		callITMMax2Row = i;
                	}
                	if(cell1.getNumericCellValue() < putOTMMax1 && cell1.getNumericCellValue() > putOTMMax2 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		putOTMMax2 = cell1.getNumericCellValue();
                		putOTMMax2Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = rowNo+1; i < sheet.getLastRowNum(); i++) {
  			    Cell cell = sheet.getRow(i).getCell(1);
  			    Cell cell1 = sheet.getRow(i).getCell(21);
  			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell.getNumericCellValue() > callOTMMax1 && cell.getNumericCellValue() < 10000000 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		callOTMMax1 = cell.getNumericCellValue();
                		callOTMMax1Row = i;
                	}
                	if(cell1.getNumericCellValue() > putITMMax1 && cell1.getNumericCellValue() < 10000000 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		putITMMax1 = cell1.getNumericCellValue();
                		putITMMax1Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = rowNo+1; i < sheet.getLastRowNum(); i++) {
  			    Cell cell = sheet.getRow(i).getCell(1);
  			  Cell cell1 = sheet.getRow(i).getCell(21);
			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell.getNumericCellValue() > callOTMMax2 && cell.getNumericCellValue() < callOTMMax1 && cell.getNumericCellValue() < 10000000 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		callOTMMax2 = cell.getNumericCellValue();
                		callOTMMax2Row = i;
                	}
                	if(cell1.getNumericCellValue() > putITMMax2 && cell1.getNumericCellValue() < putITMMax1 && cell1.getNumericCellValue() < 10000000 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		putITMMax2 = cell1.getNumericCellValue();
                		putITMMax2Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            rowNos.add(callITMMax1Row);
            rowNos.add(callITMMax2Row);
            rowNos.add(callOTMMax1Row);
            rowNos.add(callOTMMax2Row);
            rowNos.add(putITMMax1Row);
            rowNos.add(putITMMax2Row);
            rowNos.add(putOTMMax1Row);
            rowNos.add(putOTMMax2Row);
            Double prevPCR = 0d;
            String getPCR = "select * from finalupdatedtest where SNO=33";
    		rs = st.executeQuery(getPCR);
    		while(rs.next()) {
    			prevPCR = rs.getDouble("PCR");
    		}    		         
            Double prevCallsoi = 0d, prevCallsiv = 0d, prevCallsltp = 0d, prevPutsoi = 0d, prevPutsiv = 0d, prevPutsltp = 0d;
            Double presentCallsoi = 0d, presentCallsiv = 0d, presentCallsltp = 0d, presentPutsoi = 0d, presentPutsiv = 0d, presentPutsltp = 0d;
            String resultcalls, resultputs;
            Double finalPrediction=0d,callsPrediction=0d,putsPrediction=0d; 
            for(Integer i: rowNos) {

            	presentCallsoi = sheet.getRow(i).getCell(1).getNumericCellValue();
            	presentCallsiv = sheet.getRow(i).getCell(4).getNumericCellValue();
            	presentCallsltp = sheet.getRow(i).getCell(5).getNumericCellValue();
            	presentPutsoi = sheet.getRow(i).getCell(21).getNumericCellValue();
            	presentPutsiv = sheet.getRow(i).getCell(18).getNumericCellValue();
            	presentPutsltp = sheet.getRow(i).getCell(17).getNumericCellValue();
            	String get = "select * from april12 where strikeprice=" + sheet.getRow(i).getCell(11).getNumericCellValue();
        		rs = st.executeQuery(get);
        		while(rs.next()) {
        			prevCallsoi = rs.getDouble("callsoi");
        			prevCallsiv = rs.getDouble("callsiv");
        			prevCallsltp = rs.getDouble("callsltp");
        			prevPutsoi = rs.getDouble("putsoi");
        			prevPutsiv = rs.getDouble("putsiv");
        			prevPutsltp = rs.getDouble("putsltp");
        		}        		
        		if(presentCallsoi>prevCallsoi && presentCallsltp>prevCallsltp && presentCallsiv>prevCallsiv && prevPCR>presentPCR) {
        			resultcalls="Long Build Up(Market go up)";
        			count1++;
        			if(callputscount<4)
        				callscount1++;
        			else if(callputscount<7)
        				putscount1++;
        		}
        		if(presentCallsoi>prevCallsoi && presentCallsltp<prevCallsltp && presentCallsiv<prevCallsiv && prevPCR>presentPCR) {
        			resultcalls="Short Build Up(Market go down)";
        			count2++;
        			if(callputscount<4)
            			callscount2++;
            		else if(callputscount<7)
            			putscount2++;
        		}
        		if(presentCallsoi<prevCallsoi && presentCallsltp<prevCallsltp && presentCallsiv<prevCallsiv && prevPCR<presentPCR) {
        			resultcalls="Call Unwinding (Market go down slightly)";
        			//count2++;
        			count2 = count2 + 0.5;
        			if(callputscount<4)
            			callscount2+=0.5;
            		else if(callputscount<7)
            			putscount2+=0.5;
        		}
        		if(presentCallsoi<prevCallsoi && presentCallsltp>prevCallsltp && presentCallsiv>prevCallsiv && prevPCR<presentPCR) {
        			resultcalls="Short Covering (Market go up slightly)";
        			count1 = count1 + 0.5;
        			if(callputscount<4)
            			callscount1+=0.5;
            		else if(callputscount<7)
            			putscount1+=0.5;
        			//count1++;
        		}
        		if(presentPutsoi>prevPutsoi && presentPutsiv>prevPutsiv && presentPutsltp>prevPutsltp && presentPCR>prevPCR) {	
        			resultputs="Long BuildUp(Market will go Down)";
        			count2++;
        			if(callputscount<4)
            			callscount2++;
            		else if(callputscount<7)
            			putscount2++;
        		}
        		if(presentPutsoi>prevPutsoi && presentPutsiv<prevPutsiv && presentPutsltp<prevPutsltp && presentPCR>prevPCR) {	
        			resultputs="Short BuildUp(Market will go up)";
        			count1++;
        			if(callputscount<4)
            			callscount1++;
            		else if(callputscount<7)
            			putscount1++;
        		}
        		if(presentPutsoi<prevPutsoi && presentPutsiv<prevPutsiv && presentPutsltp<prevPutsltp && presentPCR<prevPCR) {	
        			resultputs="Put Unwinding (Market will go up slightly)";
        			//count1++;
        			count1 = count1 + 0.5;
        			if(callputscount<4)
            			callscount1+=0.5;
            		else if(callputscount<7)
            			putscount1+=0.5;
        		}
        		if(presentPutsoi<prevPutsoi && presentPutsiv>prevPutsiv && presentPutsltp>prevPutsltp && presentPCR<prevPCR) {	
        			resultputs="Short Covering (Market will go down slightly)";
        			//count2++;
        			count2 = count2 + 0.5;
        			if(callputscount<4)
            			callscount2+=0.5;
            		else if(callputscount<7)
            			putscount2+=0.5;
        		}
        		callputscount++;
            }        	
            if(callscount1>callscount2) {
            	callsPrediction=1d;
            }
            else if(callscount1<callscount2) {
            	callsPrediction=-1d;
            }
            else {
               	callsPrediction=0d;
            }
            if(putscount1>putscount2) {
            	putsPrediction=1d;
            }
            else if(putscount1<putscount2) {
            	putsPrediction=-1d;
            }
            else {
            	putsPrediction=0d;
            }
            if(count1>count2 && count1 > 1) {
            	finalPrediction=1d;
            	//System.out.println("Bullish");
            }
            else if(count2>count1 && count2 > 1) {
             	finalPrediction=-1d;
            	//System.out.println("Bearish");
            }
            else {     	
            	finalPrediction=0d;
            	//System.out.println("side ways");
            }
            finalResult.add(finalPrediction);
            finalResult.add(callsPrediction);
            finalResult.add(putsPrediction);
            finalResult.add(strikePrice);
            finalResult.add(callscount1);
            finalResult.add(callscount2);
            finalResult.add(putscount1);
            finalResult.add(putscount2);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }
		return finalResult;
	}

	public void insertFiiDiiResult() {
		try {
		List<Double> list = this.parameterFiiDiiCalc();
		Double fiiDiff = list.get(0);
		Double diiDiff = list.get(1);
        String query = "INSERT INTO fiidii (fiidifference,diidifference,date) "
                + "VALUES ("+ fiiDiff +","+ diiDiff +",'April12')";
        st.executeUpdate(query); 
		} catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	
	public List<FiiDiiData> getFiiDiiData() {
		List<FiiDiiData> listResult = new ArrayList<FiiDiiData>();		
		try {
			String get = "select * from fiidii";
			rs = st.executeQuery(get);
			while(rs.next()) {
				FiiDiiData today = new FiiDiiData();
				Double fiiDiff = rs.getDouble("fiidifference");
				Double diiDiff = rs.getDouble("diidifference");
				String date = rs.getString("date");
				today.setFii(fiiDiff);
				today.setDii(diiDiff);
				today.setDate(date);
				listResult.add(today);
			}
		} catch(Exception exception) {
			System.out.println("Error: " + exception.getMessage());
		}
		return listResult;
	}
	
	public List<Double> parameterPremiumDecay() {
		List<Double> result = new ArrayList<Double>();
		Double prediction = 0d;
		List<Integer> callsRowNos = new ArrayList<Integer>();
		List<Integer> putsRowNos = new ArrayList<Integer>();
		List<Double> callPList = new ArrayList<Double>();
		List<Double> putPList = new ArrayList<Double>();
		Double callAvg = 0d, putAvg = 0d, callDiff = 0d, putDiff = 0d;
        Double strikePrice = this.strikePriceCalc();  
    	Double callOTMMax1 = 0d, callOTMMax2 = 0d, callOTMMax3 = 0d, callOTMMax4 = 0d;
    	Double putOTMMax1 = 0d, putOTMMax2 = 0d, putOTMMax3 = 0d, putOTMMax4 = 0d;
    	Integer putOTMMax1Row = 0, putOTMMax2Row = 0, putOTMMax3Row = 0, putOTMMax4Row = 0;
    	Integer rowNo = 0, callOTMMax1Row = 0, callOTMMax2Row = 0, callOTMMax3Row = 0, callOTMMax4Row = 0;
		try
        {
            FileInputStream file = new FileInputStream(new File("//ad.infosys.com/storage/GEC/TRAINEE/Projects/Interns2019/Jan19_Interns_Batch1/Jan19_Interns_Batch1_Mysore/2525-JEE/ExcelSheets/DBImport.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
            	Row row = rowIterator.next();
                Cell cell = row.getCell(11);
                if(cell.getNumericCellValue()==strikePrice) {
                    rowNo = row.getRowNum(); 
                    break;
                }                    
            }
            for(int i = 0; i < rowNo; i++) {
  			    Cell cell1 = sheet.getRow(i).getCell(21);
  			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell1.getNumericCellValue() > putOTMMax1 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		putOTMMax1 = cell1.getNumericCellValue();
                		putOTMMax1Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = 0; i < rowNo; i++) {
  			    Cell cell1 = sheet.getRow(i).getCell(21);
  			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell1.getNumericCellValue() < putOTMMax1 && cell1.getNumericCellValue() > putOTMMax2 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		putOTMMax2 = cell1.getNumericCellValue();
                		putOTMMax2Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = 0; i < rowNo; i++) {
  			    Cell cell1 = sheet.getRow(i).getCell(21);
  			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell1.getNumericCellValue() < putOTMMax2 && cell1.getNumericCellValue() > putOTMMax3 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		putOTMMax3 = cell1.getNumericCellValue();
                		putOTMMax3Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = 0; i < rowNo; i++) {
  			    Cell cell1 = sheet.getRow(i).getCell(21);
  			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell1.getNumericCellValue() < putOTMMax3 && cell1.getNumericCellValue() > putOTMMax4 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		putOTMMax4 = cell1.getNumericCellValue();
                		putOTMMax4Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = rowNo+1; i < sheet.getLastRowNum(); i++) {
  			    Cell cell = sheet.getRow(i).getCell(1);
  			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell.getNumericCellValue() > callOTMMax1 && cell.getNumericCellValue() < 10000000 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		callOTMMax1 = cell.getNumericCellValue();
                		callOTMMax1Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = rowNo+1; i < sheet.getLastRowNum(); i++) {
  			    Cell cell = sheet.getRow(i).getCell(1);
			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell.getNumericCellValue() > callOTMMax2 && cell.getNumericCellValue() < callOTMMax1 && cell.getNumericCellValue() < 10000000 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		callOTMMax2 = cell.getNumericCellValue();
                		callOTMMax2Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = rowNo+1; i < sheet.getLastRowNum(); i++) {
  			    Cell cell = sheet.getRow(i).getCell(1);
  			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell.getNumericCellValue() > callOTMMax3 && cell.getNumericCellValue() < callOTMMax2 && cell.getNumericCellValue() < 10000000 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		callOTMMax3 = cell.getNumericCellValue();
                		callOTMMax3Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            for(int i = rowNo+1; i < sheet.getLastRowNum(); i++) {
  			    Cell cell = sheet.getRow(i).getCell(1);
			    Cell c = sheet.getRow(i).getCell(2);
                try {
                	if(cell.getNumericCellValue() > callOTMMax4 && cell.getNumericCellValue() < callOTMMax3 && cell.getNumericCellValue() < 10000000 && (c.getNumericCellValue() >= 0 || c.getNumericCellValue() <= 0)) {
                		callOTMMax4 = cell.getNumericCellValue();
                		callOTMMax4Row = i;
                	}
                } catch(Exception e) {
                	break;
                } 
 		    }
            callsRowNos.add(callOTMMax1Row);
            callsRowNos.add(callOTMMax2Row);
            callsRowNos.add(callOTMMax3Row);
            callsRowNos.add(callOTMMax4Row);
            putsRowNos.add(putOTMMax1Row);
            putsRowNos.add(putOTMMax2Row);
            putsRowNos.add(putOTMMax3Row);
            putsRowNos.add(putOTMMax4Row);
            Collections.sort(callsRowNos);
            Collections.sort(putsRowNos);
            for(int i = 0; i < 4; i++) {
            	callPList.add(sheet.getRow(callsRowNos.get(i)).getCell(5).getNumericCellValue());
            }
            for(int i = 0; i < 3; i++) {
            	callDiff += callPList.get(i+1) - callPList.get(i);
            }
            for(int i = 0; i < 4; i++) {
            	putPList.add(sheet.getRow(putsRowNos.get(i)).getCell(17).getNumericCellValue());
            }
            for(int i = 0; i < 3; i++) {
            	putDiff += putPList.get(i) - putPList.get(i+1);
            }
            callAvg = Math.round(callDiff/3 * 100D)/100D;
            putAvg = Math.round(putDiff/3 * 100D)/100D;            
    		if(callAvg > putAvg) {
    			prediction = -1d;
    		} else if(callAvg < putAvg) {
    			prediction = 1d;
    		} else {
    			prediction = 0d;
    		}
    		result.add(callAvg);
    		result.add(putAvg);
    		result.add(prediction);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }
		return result;
	}
	
}