/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deutschebank;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerExample 
{
    private static final Logger LOGGER = Logger.getLogger(LoggerExample.class.getName());
    public static void execute(String[] args) throws SecurityException, IOException 
    {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause ArrayIndexOutOfBoundsException");
        
        LOGGER.config("Config has been called");
    }
}