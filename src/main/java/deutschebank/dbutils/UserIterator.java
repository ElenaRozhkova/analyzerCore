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
public class UserIterator
{
   ResultSet rowIterator;

   UserIterator( ResultSet rs )
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

   public   String  getUserId() throws SQLException
   {
      return rowIterator.getString("user_id");
   }

   public   String  getUserPwd() throws SQLException
   {
      return rowIterator.getString("user_pwd");
   }

   User   buildUser() throws SQLException
   {
       User result = new User( getUserId(), getUserPwd());
       
       return result;
   }
}
