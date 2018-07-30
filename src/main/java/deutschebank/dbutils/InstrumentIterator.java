/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deutschebank.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Selvyn
 */
public class InstrumentIterator
{
   ResultSet rowIterator;

   InstrumentIterator( ResultSet rs )
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

   public   String  getInstrumentName() throws SQLException
   {
      return rowIterator.getString("instrument_name");
   }

   public   int  getInstrumentID() throws SQLException
   {
      return rowIterator.getInt("instrument_id");
   }

   Instrument   buildInstrument() throws SQLException
   {
       Instrument result = new Instrument( getInstrumentID(), getInstrumentName() );
       
       return result;
   }
}
