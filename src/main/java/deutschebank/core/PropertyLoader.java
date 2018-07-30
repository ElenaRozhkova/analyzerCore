/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deutschebank.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Selvyn
 */
public class PropertyLoader
{
    private Properties itsProperties = new Properties();
    private InputStream inputStream;
    static  private PropertyLoader itsSelf = null;
    
    private PropertyLoader(){}
    
    static  public  PropertyLoader  getLoader()
    {
        if( itsSelf == null )
            itsSelf = new PropertyLoader();
        return itsSelf;
    }
    
    public Properties getPropValues( String propFileName ) throws IOException
    {
        try
        {
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) 
            {
                    itsProperties.load(inputStream);
            } 
            else 
            {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } 
        catch(Exception e)
        {
            System.out.println("Exception: " + e);
        } 
        finally 
        {
            if( inputStream != null)
                inputStream.close();
        }
        return itsProperties;
    }   
}
