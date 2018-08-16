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

public class DealHandler {
static  private DealHandler itsSelf = null;
    
    private DealHandler(){}
    
    static  public  DealHandler  getLoader()
    {
        if( itsSelf == null )
            itsSelf = new DealHandler();
        return itsSelf;
    }
    
    public  Deal  loadFromDB( String dbID, Connection theConnection, int key )
    {
        Deal result = null;
        try
        {
            String sbQuery = "select * from " + dbID + ".deal where deal_id=?";
            PreparedStatement stmt = theConnection.prepareStatement(sbQuery);            
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            
            DealIterator iter = new DealIterator(rs);
            
            while( iter.next() )
            {
                result = iter.buildDeal();
                if(MainUnit.debugFlag) 
                    System.out.println( 
                    		result.getDealID() + "//" + 
                    		result.getDealTime() + "//" + 
                    		result.getDealCounterpartyID() + "//" + 
                    		result.getDealInstrumetID() + "//" + 
                    		result.getDealType() + "//" + 
                    		result.getDealAmount() + "//" + 
                    		result.getDealQuantity() );
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    // TODO: DO I NEED THAT ?

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
//            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return result;
//    }
    
    public  ArrayList<Deal>  loadFromDB( String dbID, Connection theConnection )
    {
        ArrayList<Deal> result = new ArrayList<Deal>();
        Deal theDeal = null;
        try
        {
            String sbQuery = "select * from " + dbID + ".deal";
            PreparedStatement stmt = theConnection.prepareStatement(sbQuery);            
            ResultSet rs = stmt.executeQuery();
            
            DealIterator iter = new DealIterator(rs);
            
            while( iter.next() )
            {
            	theDeal = iter.buildDeal();
                if(MainUnit.debugFlag) 
                	System.out.println( 
                			theDeal.getDealID() + "//" + 
                			theDeal.getDealTime() + "//" + 
                			theDeal.getDealCounterpartyID() + "//" + 
                			theDeal.getDealInstrumetID() + "//" + 
                			theDeal.getDealType() + "//" + 
                			theDeal.getDealAmount() + "//" + 
                			theDeal.getDealQuantity() );
              
                result.add(theDeal);
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public  String  toJSON( Deal theDeal )
    {
        String result = "";
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(theDeal);
        } 
        catch (JsonProcessingException ex)
        {
            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public  String  toJSON( ArrayList<Deal> theDeals )
    {
        String result = "";
        Deal[] insArray = new Deal[theDeals.size()];
        theDeals.toArray(insArray);
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(insArray);
        } 
        catch (JsonProcessingException ex)
        {
            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
