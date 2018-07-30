/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deutschebank;

import deutschebank.dbutils.DBConnector;
import deutschebank.dbutils.Instrument;
import deutschebank.dbutils.InstrumentHandler;
import deutschebank.dbutils.PropertyLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import deutschebank.dbutils.User;
import deutschebank.dbutils.UserHandler;
import java.io.File;
import java.util.logging.FileHandler;

/**
 *
 * @author Selvyn
 */
public class MainUnit
{
   private static final Logger LOGGER = Logger.getLogger(LoggerExample.class.getName());
   public  static  boolean debugFlag = true;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            DBConnector connector = DBConnector.getConnector();
            
            PropertyLoader pLoader = PropertyLoader.getLoader();
            
            Properties pp = pLoader.getPropValues( "dbConnector.properties" );
            
            connector.connect( pp );
            
            InstrumentHandler theInstrumentHandler = InstrumentHandler.getLoader();
            Instrument theInstrument = theInstrumentHandler.loadFromDB(pp.getProperty("dbName"), connector.getConnection(), 2);
            
            if( theInstrument != null )
            {
                System.out.println( theInstrument.getInstrumentID() + "//" + theInstrument.getInstrumentName() );
            }

            ArrayList<Instrument> theInstruments = theInstrumentHandler.loadFromDB(pp.getProperty("dbName"), connector.getConnection(), 2, 10);
            
            Instrument[] insArray = new Instrument[theInstruments.size()];
            theInstruments.toArray(insArray);
            theInstruments.forEach( (instrument)->
                {
                    System.out.println( instrument.getInstrumentID() + "//" + instrument.getInstrumentName() );
                }
            );
            
            // Now convert the Instrument instane into a JSON object
            ObjectMapper mapper = new ObjectMapper();
            // Convert object to JSON string and save into a file directly
            mapper.writeValue(new File("instrument.json"), theInstrument);

            // Convert an array of objects to JSON string and save into a file directly
            mapper.writeValue(new File("instrument_array.json"), insArray);

            // Convert object to JSON string
            String jsonInString = mapper.writeValueAsString(theInstrument);
            System.out.println(jsonInString);

            // Convert object to JSON string and pretty print
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(theInstrument);
            System.out.println(jsonInString);

            //========================================================================
            // Working with user table
            //========================================================================
            UserHandler theUserHandler = UserHandler.getLoader();
            User theUser = theUserHandler.loadFromDB(connector.getConnection(), "selvyn", "gradprog2016");
            
            if( theUser != null )
            {
                System.out.println( theUser.getUserID()+ "//" + theUser.getUserPwd());
            }

            // Convert object to JSON string and save into a file directly
            mapper.writeValue(new File("user.json"), theUser);

        } 
        catch (IOException ex)
        {
            Logger.getLogger(MainUnit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  static  void    log( String msg )
    {
        //if( debugFlag )
        {
            LOGGER.info( msg );
            System.out.println( msg );
        }
    }
    
}
