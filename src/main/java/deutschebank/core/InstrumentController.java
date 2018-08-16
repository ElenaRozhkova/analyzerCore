package deutschebank.core;

import java.util.ArrayList;

import deutschebank.dbutils.Instrument;
import deutschebank.dbutils.InstrumentHandler;

public class InstrumentController
{
    final   private ApplicationScopeHelper ash = new ApplicationScopeHelper();

    public String getAllInstruments() {
		String result = null;
		ArrayList<Instrument> instruments = ash.getAllInstruments();
		result = InstrumentHandler.getLoader().toJSON(instruments);
		return result;
	}
}
