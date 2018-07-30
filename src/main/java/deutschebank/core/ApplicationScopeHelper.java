/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deutschebank.core;

import deutschebank.MainUnit;
import deutschebank.dbutils.DBConnector;
import deutschebank.dbutils.User;
import deutschebank.dbutils.UserHandler;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Selvyn
 */
public class ApplicationScopeHelper
{

    private String itsInfo = "NOT SET";
    private DBConnector itsConnector = null;

    public String getInfo()
    {
        return itsInfo;
    }

    public void setInfo(String itsInfo)
    {
        this.itsInfo = itsInfo;
    }

    public boolean bootstrapDBConnection()
    {
        boolean result = false;
        if (itsConnector == null)
        {
            try
            {
                itsConnector = DBConnector.getConnector();

                PropertyLoader pLoader = PropertyLoader.getLoader();

                Properties pp;
                pp = pLoader.getPropValues("dbConnector.properties");

                result = itsConnector.connect(pp);
            } catch (IOException ex)
            {
                Logger.getLogger(ApplicationScopeHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            result = true;
        
        return result;
    }
    
    public User userLogin( String userId, String userPwd )
    {
        User theUser = null;
        try
        {
            UserHandler theUserHandler = UserHandler.getLoader();
            
            theUser = theUserHandler.loadFromDB(DBConnector.getConnector().getConnection(), userId, userPwd );
            
            if( theUser != null )
                MainUnit.log( "User " + userId + " has logged into system");
        } catch (IOException ex)
        {
            Logger.getLogger(ApplicationScopeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return theUser;
    }

}
