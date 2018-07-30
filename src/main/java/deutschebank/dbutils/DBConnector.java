/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deutschebank.dbutils;

import deutschebank.MainUnit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Selvyn
 */
public class DBConnector
{
    static  private DBConnector itsSelf = null;
    
    private Connection itsConnection;
    private String  dbDriver ="";   //  "com.mysql.jdbc.Driver";
    private String  dbPath = "";    //  "jdbc:mysql://52.209.91.145/";
    private String  dbName = "";    //  "db_grad_cs_1916";
    private String  dbUser = "";    //  "selvyn";
    private String  dbPwd = "";     //  "dbGradProg2016";

    static  public  DBConnector getConnector() throws IOException
    {
        if( itsSelf == null )
             itsSelf = new DBConnector();
        return itsSelf;
    }
    private DBConnector(){}
    
    public  Connection  getConnection()
    {
        return itsConnection;
    }
    
    public  boolean    connect( Properties properties )
    {
        boolean result = false;
        try
        {
            MainUnit.log("On Entry -> DBConnector.connect()");
            
            dbDriver = properties.getProperty("dbDriver");
            dbPath = properties.getProperty("dbPath");
            dbName = properties.getProperty("dbName");
            dbUser = properties.getProperty("dbUser");
            dbPwd = properties.getProperty("dbPwd");
          
            Class.forName( dbDriver );

            itsConnection = DriverManager.getConnection(dbPath + dbName, 
                                                    dbUser, 
                                                    dbPwd );

            /*
            MainUnit.log( itsConnection.getCatalog() );

            DatabaseMetaData metaInfo = itsConnection.getMetaData();

            // The following call returns a result set with following columns
            // TABLE_SCHEM String => schema name
            // TABLE_CATALOG String => catalog name (may be null)
            ResultSet rs = metaInfo.getSchemas();

            CatalogInfoIterator cursor = new CatalogInfoIterator( rs );

            while( cursor.next() )
            {
                System.out.println( cursor.getTable_Schema() + "/" + cursor.getTable_Catalog() );
            }

            rs.close();   
            */
            MainUnit.log( "Successfully connected to " + dbName );
            
            result = true;
        }
        catch( ClassNotFoundException | SQLException e )
        {
           e.printStackTrace();
        }
        
        MainUnit.log("On Exit -> DBConnector.connect()");

        return result;
    }
}
