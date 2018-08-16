package deutschebank.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CounterpartyIterator {
	   ResultSet rowIterator;

	   CounterpartyIterator( ResultSet rs )
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
	   
	   public   int  getCounterpartyID() throws SQLException
	   {
	      return rowIterator.getInt("counterparty_id");
	   }

	   public   String  getCounterpartyName() throws SQLException
	   {
	      return rowIterator.getString("counterparty_name");
	   }
	   
	   public   String  getCounterpartyStatus() throws SQLException
	   {
	      return rowIterator.getString("counterparty_status");
	   }
	   
	   public   Timestamp  getCounterpartyDateRegistered() throws SQLException
	   {
	      return rowIterator.getTimestamp("counterparty_date_registered");
	   }

	   Counterparty   buildCounterparty() throws SQLException
	   {
		   Counterparty result = new Counterparty( getCounterpartyID(), getCounterpartyName(), getCounterpartyStatus(), getCounterpartyDateRegistered());
	       
	       return result;
	   }
	}

