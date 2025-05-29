
import com.infy.dao.StockDAOImpl;

public class Tester {
	public static void main(String[] args) {
		StockDAOImpl s = new StockDAOImpl();
		//s.strikePriceCalc();
		//s.parameterOICalc();
		//s.parameterATMCalc();
		//s.parameterPCRCalc();
		//s.parameterPremiumDecay();
		//s.insertIntoDB();
		//s.parameterRSCalc();
		//s.getDataFromDB();
		//s.parameterVIXCalc();
		//s.insertOIChangeTodayData();
		s.finalPrediction();
		//s.directPredictFromWeb();
		//s.parameterFiiDiiCalc();
		//s.insertFiiDiiResult();
		//s.getFiiDiiData();
	}
}
