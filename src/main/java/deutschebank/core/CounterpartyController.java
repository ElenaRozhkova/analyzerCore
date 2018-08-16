package deutschebank.core;

import java.util.ArrayList;

import deutschebank.dbutils.Counterparty;
import deutschebank.dbutils.CounterpartyHandler;


public class CounterpartyController
{
    final   private ApplicationScopeHelper ash = new ApplicationScopeHelper();
    
    public String getAllCounterparties() {
		String result = null;
		ArrayList<Counterparty> counterparties = ash.getAllCounterparties();
		result = CounterpartyHandler.getLoader().toJSON(counterparties);
		return result;
    }

}