/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deutschebank.dbutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import deutschebank.MainUnit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Selvyn
 */
public class InstrumentHandler
{
    static  private InstrumentHandler itsSelf = null;
    
    private InstrumentHandler(){}
    
    static  public  InstrumentHandler  getLoader()
    {
        if( itsSelf == null )
            itsSelf = new InstrumentHandler();
        return itsSelf;
    }
    
    public  Instrument  loadFromDB( String dbID, Connection theConnection, int key )
    {
        Instrument result = null;
        try
        {
            String sbQuery = "select * from " + dbID + ".instrument where instrument_id=?";
            PreparedStatement stmt = theConnection.prepareStatement(sbQuery);            
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            
            InstrumentIterator iter = new InstrumentIterator(rs);
            
            while( iter.next() )
            {
                result = iter.buildInstrument();
                if(MainUnit.debugFlag)
                    System.out.println( result.getInstrumentID() + "//" + result.getInstrumentName() );
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }

    public  ArrayList<Instrument>  loadFromDB( String dbID, Connection theConnection, int lowerKey, int upperKey )
    {
        ArrayList<Instrument> result = new ArrayList<Instrument>();
        Instrument theInstrument = null;
        try
        {
            String sbQuery = "select * from " + dbID + ".instrument where instrument_id>=? and instrument_id<=?";
            PreparedStatement stmt = theConnection.prepareStatement(sbQuery);            
            stmt.setInt(1, lowerKey);
            stmt.setInt(2, upperKey);
            ResultSet rs = stmt.executeQuery();
            
            InstrumentIterator iter = new InstrumentIterator(rs);
            
            while( iter.next() )
            {
                theInstrument = iter.buildInstrument();
                if(MainUnit.debugFlag)
                    System.out.println( theInstrument.getInstrumentID() + "//" + theInstrument.getInstrumentName() );
                result.add(theInstrument);
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public  ArrayList<Instrument>  loadFromDB( String dbID, Connection theConnection )
    {
        ArrayList<Instrument> result = new ArrayList<Instrument>();
        Instrument theInstrument = null;
        try
        {
            String sbQuery = "select * from " + dbID + ".instrument";
            PreparedStatement stmt = theConnection.prepareStatement(sbQuery);            
            ResultSet rs = stmt.executeQuery();
            
            InstrumentIterator iter = new InstrumentIterator(rs);
            
            while( iter.next() )
            {
                theInstrument = iter.buildInstrument();
                if(MainUnit.debugFlag)
                    System.out.println( theInstrument.getInstrumentID() + "//" + theInstrument.getInstrumentName() );
                result.add(theInstrument);
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(InstrumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public  String  toJSON( Instrument theInstrument )
    {
        String result = "";
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(theInstrument);
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

    public  String  toJSON( ArrayList<Instrument> theInstruments )
    {
        String result = "";
        Instrument[] insArray = new Instrument[theInstruments.size()];
        theInstruments.toArray(insArray);
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
