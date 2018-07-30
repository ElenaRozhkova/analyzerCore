/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package deutschebank.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author selvyn
 */
public class CatalogInfoIterator 
{
   ResultSet rowIterator;

   CatalogInfoIterator( ResultSet rs )
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

   // TABLE_SCHEM String => schema name
   // TABLE_CATALOG String => catalog name (may be null)

   public   String  getTable_Schema() throws SQLException
   {
      return rowIterator.getString("TABLE_SCHEM");
   }

   public   String  getTable_Catalog() throws SQLException
   {
      return rowIterator.getString("TABLE_CATALOG");
   }
}
