package deutschebank.dbutils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import deutschebank.MainUnit;

public class CounterpartyHandler {
static  private CounterpartyHandler itsSelf = null;
    
    private CounterpartyHandler(){}
    
    static  public  CounterpartyHandler  getLoader()
    {
        if( itsSelf == null )
            itsSelf = new CounterpartyHandler();
        return itsSelf;
    }
    
    public  Counterparty  loadFromDB( String dbID, Connection theConnection, int key )
    {
        Counterparty result = null;
        try
        {
            String sbQuery = "select * from " + dbID + ".counterparty where counterparty_id=?";
            PreparedStatement stmt = theConnection.prepareStatement(sbQuery);            
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            
            CounterpartyIterator iter = new CounterpartyIterator(rs);
            
            while( iter.next() )
            {
                result = iter.buildCounterparty();
                if(MainUnit.debugFlag)
                    System.out.println( 
                    		result.getCounterpartyID() + "//" + 
                    		result.getCounterpartyName() + "//" + 
                    		result.getCounterpartyStatus() + "//" + 
                    		result.getCounterpartyDateRegistered() );
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(CounterpartyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    // TODO: DO I NEED IT ?

//    public  ArrayList<Instrument>  loadFromDB( String dbID, Connection theConnection, int lowerKey, int upperKey )
//    {
//        ArrayList<Instrument> result = new ArrayList<Instrument>();
//        Instrument theInstrument = null;
//        try
//        {
//            String sbQuery = "select * from " + dbID + ".instrument where instrument_id>=? and instrument_id<=?";
//            PreparedStatement stmt = theConnection.prepareStatement(sbQuery);            
//            stmt.setInt(1, lowerKey);
//            stmt.setInt(2, upperKey);
//            ResultSet rs = stmt.executeQuery();
//            
//            InstrumentIterator iter = new InstrumentIterator(rs);
//            
//            while( iter.next() )
//            {
//                theInstrument = iter.buildInstrument();
//                if(MainUnit.debugFlag)
//                    System.out.println( theInstrument.getInstrumentID() + "//" + theInstrument.getInstrumentName() );
//                result.add(theInstrument);
//            }
//        } 
//        catch (SQLException ex)
//        {
//            Logger.getLogger(CounterpartyHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return result;
//    }
    
    public  ArrayList<Counterparty>  loadFromDB( String dbID, Connection theConnection )
    {
        ArrayList<Counterparty> result = new ArrayList<Counterparty>();
        Counterparty theCounterparty = null;
        try
        {
            String sbQuery = "select * from " + dbID + ".instrument";
            PreparedStatement stmt = theConnection.prepareStatement(sbQuery);            
            ResultSet rs = stmt.executeQuery();
            
            CounterpartyIterator iter = new CounterpartyIterator(rs);
            
            while( iter.next() )
            {
            	theCounterparty = iter.buildCounterparty();
                if(MainUnit.debugFlag) 
                    System.out.println( 
                    		theCounterparty.getCounterpartyID() + "//" + 
                    		theCounterparty.getCounterpartyName() + "//" + 
                    		theCounterparty.getCounterpartyStatus() + "//" + 
                    		theCounterparty.getCounterpartyDateRegistered() );
                
                result.add(theCounterparty);
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(CounterpartyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public  String  toJSON( Counterparty theCounterparty )
    {
        String result = "";
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(theCounterparty);
        } 
        catch (JsonProcessingException ex)
        {
            Logger.getLogger(CounterpartyHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(CounterpartyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public  String  toJSON( ArrayList<Counterparty> theCounterparties )
    {
        String result = "";
        Counterparty[] insArray = new Counterparty[theCounterparties.size()];
        theCounterparties.toArray(insArray);
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(insArray);
        } 
        catch (JsonProcessingException ex)
        {
            Logger.getLogger(CounterpartyHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(CounterpartyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}