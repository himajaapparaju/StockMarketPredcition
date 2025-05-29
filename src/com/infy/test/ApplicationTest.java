package com.infy.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import com.infy.dao.StockDAOImpl;

public class ApplicationTest {

	@Test
	public void validParameterATMTest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterATMCalc();
		Double strikePrice = s.strikePriceCalc();
		Double callLtp=0d,putLtp=0d;
		List<Double> expectedResult = new ArrayList<Double>();
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
            expectedResult.add(callLtp);
            expectedResult.add(putLtp);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }
		Assert.assertEquals(expectedResult.get(0), actualResult.get(0),0.01);
		Assert.assertEquals(expectedResult.get(1), actualResult.get(1),0.01);
	}
	
	@Test
	public void invalidParameterATMTest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterATMCalc();
		Double strikePrice = s.strikePriceCalc();
		Double callLtp=0d,putLtp=0d;
		List<Double> expectedResult = new ArrayList<Double>();
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
            expectedResult.add(callLtp);
            expectedResult.add(putLtp);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }
		Assert.assertNotEquals(expectedResult.get(0), actualResult.get(1),0.01);
		Assert.assertNotEquals(expectedResult.get(1), actualResult.get(0),0.01);
	}
	
	@Test
	public void validParameterPCRTest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterPCRCalc();
		Double ratio = 0d,totalCall = 0d, totalPut = 0d;
		List<Double> expectedResult = new ArrayList<Double>();
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
            ratio = totalPut/totalCall;
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
		}
        expectedResult.add(ratio);
        expectedResult.add(-5d);
        Assert.assertEquals(expectedResult.get(0), actualResult.get(0),0.01);
	}
	
	@Test
	public void invalidParameterPCRTest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterPCRCalc();
		Double ratio = 0d,totalCall = 0d, totalPut = 0d;
		List<Double> expectedResult = new ArrayList<Double>();
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
            ratio = totalPut/totalCall;
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
		}
        expectedResult.add(ratio);
        expectedResult.add(-5d);
        Assert.assertNotEquals(expectedResult.get(0), actualResult.get(1),0.01);
	}
	
	@Test
	public void validParameterVIXTest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterVIXCalc();
		Double vix = 0d;
		List<Double> expectedResult = new ArrayList<Double>();
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
        	expectedResult.add(vix);
        	expectedResult.add(-5d);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
		}
        Assert.assertEquals(expectedResult.get(0), actualResult.get(0),0.01);
	}
	
	@Test
	public void invalidParameterVIXTest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterVIXCalc();
		Double vix = 0d;
		List<Double> expectedResult = new ArrayList<Double>();
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
        	expectedResult.add(vix);
        	expectedResult.add(-5d);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
		}
        Assert.assertNotEquals(expectedResult.get(0), actualResult.get(1),0.01);
	}
	
	@Test
	public void validParameterIVTest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterIVCalc();
		Double strikePrice = s.strikePriceCalc();
		Double callsIV = 0d, putsIV = 0d;
		List<Double> expectedResult = new ArrayList<Double>();
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
            expectedResult.add(callsIV);
            expectedResult.add(putsIV);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }
		Assert.assertEquals(expectedResult.get(0), actualResult.get(0),0.01);
		Assert.assertEquals(expectedResult.get(1), actualResult.get(1),0.01);
	}
	
	@Test
	public void invalidParameterIVTest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterIVCalc();
		Double strikePrice = s.strikePriceCalc();
		Double callsIV = 0d, putsIV = 0d;
		List<Double> expectedResult = new ArrayList<Double>();
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
            expectedResult.add(callsIV);
            expectedResult.add(putsIV);
            workbook.close();
            file.close();
        }
        catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }
		Assert.assertNotEquals(expectedResult.get(0), actualResult.get(1),0.01);
		Assert.assertNotEquals(expectedResult.get(1), actualResult.get(0),0.01);
	}

	@Test
	public void validParameterOITest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterOICalc();
		Double strikePrice = s.strikePriceCalc();
		Double callsoi=0d,putsoi=0d;
		int rowNo = 0;
	    List<Double> expectedResult = new ArrayList<Double>();
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
		    putsoi= sheet.getRow(rowNo).getCell(20).getNumericCellValue();	    
		    expectedResult.add(callsoi);
		    expectedResult.add(putsoi);
		    workbook.close();
		    file.close();
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		Assert.assertEquals(expectedResult.get(0), actualResult.get(0),0.01);
		Assert.assertEquals(expectedResult.get(1), actualResult.get(1),0.01);
	}

	@Test
	public void invalidParameterOITest() {
		StockDAOImpl s = new StockDAOImpl();
		List<Double> actualResult = s.parameterOICalc();
		Double strikePrice = s.strikePriceCalc();
		Double callsoi=0d,putsoi=0d;
		int rowNo = 0;
	    List<Double> expectedResult = new ArrayList<Double>();
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
		    putsoi= sheet.getRow(rowNo).getCell(20).getNumericCellValue();	    
		    expectedResult.add(callsoi);
		    expectedResult.add(putsoi);
		    workbook.close();
		    file.close();
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		Assert.assertNotEquals(expectedResult.get(0), actualResult.get(1),0.01);
		Assert.assertNotEquals(expectedResult.get(1), actualResult.get(0),0.01);
	}
	
}