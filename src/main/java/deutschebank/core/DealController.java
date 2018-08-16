package deutschebank.core;

import java.util.ArrayList;

import deutschebank.dbutils.Deal;
import deutschebank.dbutils.DealHandler;

public class DealController {
    final   private ApplicationScopeHelper ash = new ApplicationScopeHelper();
    
    public String getAllDeals() {
		String result = null;
		ArrayList<Deal> deals = ash.getAllDeals();
		result = DealHandler.getLoader().toJSON(deals);
		return result;	
    }

}