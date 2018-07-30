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
public class UserHandler
{
    static  private UserHandler itsSelf = null;
    
    private UserHandler(){}
    
    static  public  UserHandler  getLoader()
    {
        if( itsSelf == null )
            itsSelf = new UserHandler();
        return itsSelf;
    }
    
    public  User  loadFromDB( Connection theConnection, String userid, String pwd )
    {
        User result = null;
        try
        {
            MainUnit.log("On Entry -> UserHandler.loadFromDB()");
            String sbQuery = "select * from db_grad_cs_1917.users where user_id=? and user_pwd=?";
            PreparedStatement stmt = theConnection.prepareStatement(sbQuery);            
            stmt.setString(1, userid);
            stmt.setString(2, pwd);
            ResultSet rs = stmt.executeQuery();
            
            UserIterator iter = new UserIterator(rs);
            
            if( iter.next() )
            {
                result = iter.buildUser();
                MainUnit.log(result.getUserID() + "//" + result.getUserPwd() );
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainUnit.log("On Exit -> UserHandler.loadFromDB()");
        
        return result;
    }

    public  String  toJSON( User theUser )
    {
        String result = "<Some value from the server>";
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(theUser);
        } 
        catch (JsonProcessingException ex)
        {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
