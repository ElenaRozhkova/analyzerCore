package deutschebank.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealIterator {
	   ResultSet rowIterator;

	   DealIterator( ResultSet rs )
	   {
	       rowIterator = rs;
	   }
	   
	   public boolean  first() throws SQLException
	   {
	      return rowIterator.first();
	   }
	   
	   public boolean last() throws SQLException
	   {
	      return rowIterator.last();
	   }
	   public boolean next() throws SQLException
	   {
	      return rowIterator.next();
	   }
	   
	   public boolean prior() throws SQLException
	   {
	      return rowIterator.previous();
	   }
	   
	   public   int  getDealID() throws SQLException
	   {
	      return rowIterator.getInt("deal_id");
	   }

	   public   String  getDealTime() throws SQLException
	   {
	      return rowIterator.getString("deal_time");
	   }
	   
	   public   int  getDealCounterpartyID() throws SQLException
	   {
	      return rowIterator.getInt("deal_counterparty_id");
	   }
	   
	   public   int  getDealInstrumentID() throws SQLException
	   {
	      return rowIterator.getInt("deal_instrument_id");
	   }
	   
	   public   String  getDealType() throws SQLException
	   {
	      return rowIterator.getString("deal_type");
	   }
	   
	   public   float  getDealAmount() throws SQLException
	   {
	      return rowIterator.getFloat("deal_amount");
	   }
	   
	   public   int  getDealQuantity() throws SQLException
	   {
	      return rowIterator.getInt("deal_quantity");
	   }

	   Deal   buildDeal() throws SQLException
	   {
	       Deal result = new Deal( getDealID(), getDealTime(), getDealCounterpartyID(), 
	    		   getDealInstrumentID(), getDealType(), getDealAmount(), getDealQuantity() );
	       
	       return result;
	   }
	}

