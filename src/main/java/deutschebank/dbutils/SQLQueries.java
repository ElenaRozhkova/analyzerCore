package deutschebank.dbutils;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import deutschebank.MainUnit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonObject;

import java.sql.*;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonArray;

import deutschebank.dbutils.User;
import deutschebank.dbutils.UserHandler;

public class SQLQueries {
	public static Connection conn = null;
	public static ResultSet rs = null;
	public static Statement stmt = null;

	public SQLQueries() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://10.0.75.1:3307/db_grad";
			conn = DriverManager.getConnection(url, "root", "ppp");

		} catch (Exception e) {
			System.err.println("Connection failed.! ");
			System.err.println(e.getClass());
		}
	}

	/* convert output to JSON objects and return String */
	/* convert output to JSON objects and return String */
	public static  String toJSON(String[][] input) {
        String result = "<Some value from the server>";
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(input);
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
	
	public static  String toJSON(String input) {
        String result = "<Some value from the server>";
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(input);
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
	
	/*********************************  FRONT PAGE ***************************************/
	//public static String avgPriceBought(int instrumentID)
	//public static String avgPriceSold(int instrumentID)
	//public static String volumeSold(int instrumentID)
	//public static String volumeBought(int instrumentID)
    //public static String[][] instrumentBuyPriceVsTime(int instrumentID)
    //public static String[][] instrumentSellPriceVsTime(int instrumentID)
//	public static String mostTradedBy(int instrumentID)

	public static String avgPriceBought(int instrumentID) {
		String result ="";
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT i.instrument_id instrument_id,\r\n" + 
					"AVG(case when d.deal_type = 'B' then d.deal_amount else 0 end) as average_price_bought\r\n" + 
					"FROM instrument i\r\n" + 
					"JOIN deal d ON d.deal_instrument_id=i.instrument_id\r\n" + 
					"WHERE i.instrument_id="
					+ instrumentID + ";");
			while (rs.next()) {
				result = rs.getString(2);
				//System.out.println(result);
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}
		return result;
	}
	
	public static String avgPriceSold(int instrumentID) {
		String result ="";
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT i.instrument_id instrument_id,\r\n" + 
					"AVG(case when d.deal_type = 'S' then d.deal_amount else 0 end) as average_price_bought\r\n" + 
					"FROM instrument i\r\n" + 
					"JOIN deal d ON d.deal_instrument_id=i.instrument_id\r\n" + 
					"WHERE i.instrument_id="
					+ instrumentID + ";");
			while (rs.next()) {
				result = rs.getString(2);
			//	System.out.println(result);
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}
		return result;
	}

	public static String volumeBought(int instrumentID)
	{
		String result ="";
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT i.instrument_id instrument_id,\r\n" + 
					"SUM(case when d.deal_type = 'B' then d.deal_quantity else 0 end) volume_bought\r\n" + 
					"FROM instrument i\r\n" + 
					"JOIN deal d ON d.deal_instrument_id=i.instrument_id\r\n" + 
					"WHERE i.instrument_id="
					+ instrumentID + ";");
			while (rs.next()) {
				result = rs.getString(2);
				//System.out.println(result);
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}
		return result;
	}
	
	public static String volumeSold(int instrumentID)
	{
		String result ="";
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT i.instrument_id instrument_id,\r\n" + 
					"SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) volume_sold\r\n" + 
					"FROM instrument i\r\n" + 
					"JOIN deal d ON d.deal_instrument_id=i.instrument_id\r\n" + 
					"WHERE i.instrument_id="
					+ instrumentID + ";");
			while (rs.next()) {
				result = rs.getString(2);
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}
		return result;
	}
	
	public static String mostTradedBy(int instrumentID)
	{
		String result ="";
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT i.instrument_id instrument_id, c.counterparty_name,SUM(d.deal_quantity) \r\n" + 
					"FROM deal d\r\n" + 
					"JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id\r\n" + 
					"JOIN instrument i ON d.deal_instrument_id=i.instrument_id\r\n" + 
					"WHERE i.instrument_id=" + instrumentID+"\r\n" + 
					"GROUP BY c.counterparty_name\r\n" + 
					"ORDER BY SUM(d.deal_quantity) DESC\r\n" + 
					"LIMIT 1;");
			while (rs.next()) {
				result = rs.getString(2);
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}
		return result;
	}
	
	
	public static String[][] instrumentBuyPriceVsTime(int instrumentID) {
		int length = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"Select COUNT(*) from deal WHERE deal_type='B' AND deal_instrument_id=" + instrumentID + ";");
			while (rs.next()) {
				length = rs.getInt(1);

				//System.out.println("The length is " + length);
			}

			stmt.close();
		}

		catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}

		String[][] result = new String[length][2];

		try {
			stmt = conn.createStatement();

			// rs = null;
			rs = stmt.executeQuery("Select deal_time, deal_amount from deal WHERE deal_type='B' AND deal_instrument_id="
					+ instrumentID + ";");
			int i = 0;
			while (rs.next()) {
			//	System.out.println(rs.getString(1) + " " + rs.getString(2));
				result[i][0] = rs.getString(1);
				result[i++][1] = rs.getString(2);
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}

		return result;
	}

	public static String[][] instrumentSellPriceVsTime(int instrumentID) {
		int length = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"Select COUNT(*) from deal WHERE deal_type='S' AND deal_instrument_id=" + instrumentID + ";");
			while (rs.next()) {
				length = rs.getInt(1);

				//System.out.println("The length is " + length);
			}

			stmt.close();
		}

		catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}

		String[][] result = new String[length][2];

		try {
			stmt = conn.createStatement();

			// rs = null;
			rs = stmt.executeQuery("Select deal_time, deal_amount from deal WHERE deal_type='B' AND deal_instrument_id="
					+ instrumentID + ";");
			int i = 0;
			while (rs.next()) {
			//	System.out.println(rs.getString(1) + " " + rs.getString(2));
				result[i][0] = rs.getString(1);
				result[i++][1] = rs.getString(2);
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}

		return result;
	}

	
	
	//
	/*****************************  END FRONT PAGE ***************************************/

	// public static String[][] rawDataTable() 
	// public static String[][] counterpartyTable()

	public static String[][] rawDataTable() {
		int length = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT d.deal_id, d.deal_time, d.deal_type, d.deal_amount,\r\n" + 
					"d.deal_quantity, i.instrument_name, c.counterparty_name\r\n" + 
					"FROM deal d\r\n" + 
					"JOIN instrument i ON d.deal_instrument_id=i.instrument_id\r\n" + 
					"JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id\r\n" + 
					"LIMIT 20;");
			while (rs.next()) {
				length = rs.getInt(1);

				//System.out.println("The length is " + length);
			}

			stmt.close();
		}

		catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}

		String[][] result = new String[length][7];

		try {
			stmt = conn.createStatement();

			// rs = null;
			rs = stmt.executeQuery("Select * from deal;");
			int i = 0;
			while (rs.next()) {
				// System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7));
				result[i][0] = rs.getString(1);
				result[i][1] = rs.getString(2);
				result[i][2] = rs.getString(3);
				result[i][3] = rs.getString(4);
				result[i][4] = rs.getString(5);
				result[i][5] = rs.getString(6);
				result[i][6] = rs.getString(7);


				
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}

		return result;
	}
	

	//total buys, total sells, net position, realized profit and effective rate for each instrument for each counterparty
	//counterpartytable
	public static String[][] counterpartyTable() {
		int length = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT COUNT(*) FROM (SELECT c.counterparty_name, i.instrument_name,\r\n" + 
					"       SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) as total_buys,\r\n" + 
					"       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as total_sells,\r\n" + 
					"       SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - \r\n" + 
					"       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as net_position,\r\n" + 
					"       (SUM(case when d.deal_type='S' then d.deal_amount else 0 end)-\r\n" + 
					"       SUM(case when d.deal_type='B' then d.deal_amount else 0 end)) as realized_profit,\r\n" + 
					"       (AVG(case when d.deal_type='S' then d.deal_amount else 0 end)-\r\n" + 
					"       AVG(case when d.deal_type='B' then d.deal_amount else 0 end)) * \r\n" + 
					"       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end)\r\n" + 
					"       +\r\n" + 
					"       ((SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - \r\n" + 
					"       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end))\r\n" + 
					"	*\r\n" + 
					"	   (SELECT deal_amount FROM deal ORDER BY deal_time desc LIMIT 1)\r\n" + 
					"       -\r\n" + 
					"       AVG(case when d.deal_type='S' then d.deal_amount else 0 end)) as effective_profit\r\n" + 
					"FROM deal d\r\n" + 
					"JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id\r\n" + 
					"JOIN instrument i ON d.deal_instrument_id=i.instrument_id\r\n" + 
					"GROUP BY c.counterparty_name,i.instrument_id) AS x;");
			while (rs.next()) {
				length = rs.getInt(1);

				//System.out.println("The length is " + length);
			}

			stmt.close();
		}

		catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}

		String[][] result = new String[length][7];

		try {
			stmt = conn.createStatement();

			// rs = null;
			rs = stmt.executeQuery("SELECT c.counterparty_name, i.instrument_name,\r\n" + 
					"       SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) as total_buys,\r\n" + 
					"       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as total_sells,\r\n" + 
					"       SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - \r\n" + 
					"       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as net_position,\r\n" + 
					"       (SUM(case when d.deal_type='S' then d.deal_amount else 0 end)-\r\n" + 
					"       SUM(case when d.deal_type='B' then d.deal_amount else 0 end)) as realized_profit,\r\n" + 
					"       (AVG(case when d.deal_type='S' then d.deal_amount else 0 end)-\r\n" + 
					"       AVG(case when d.deal_type='B' then d.deal_amount else 0 end)) * \r\n" + 
					"       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end)\r\n" + 
					"       +\r\n" + 
					"       ((SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - \r\n" + 
					"       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end))\r\n" + 
					"	*\r\n" + 
					"	   (SELECT deal_amount FROM deal ORDER BY deal_time desc LIMIT 1)\r\n" + 
					"       -\r\n" + 
					"       AVG(case when d.deal_type='S' then d.deal_amount else 0 end)) as effective_profit\r\n" + 
					"FROM deal d\r\n" + 
					"JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id\r\n" + 
					"JOIN instrument i ON d.deal_instrument_id=i.instrument_id\r\n" + 
					"GROUP BY c.counterparty_name,i.instrument_id;");
			int i = 0;
			while (rs.next()) {
				 System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7));
				result[i][0] = rs.getString(1);
				result[i][1] = rs.getString(2);
				result[i][2] = rs.getString(3);
				result[i][3] = rs.getString(4);
				result[i][4] = rs.getString(5);
				result[i][5] = rs.getString(6);
				result[i][6] = rs.getString(7);


				
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("SQL query failed.");
			System.err.println(e.getClass().getName());
		}

		return result;
	}


	
	

}



/*
 * public static void averagePrices(String startDate, String finishDate,
 * Statement stmt) throws SQLException {
 * 
 * String query = "select\n" + "i.instrument_name,\n" +
 * "sum(case when d.deal_type = 'S' then d.deal_amount else 0 end)/sum(d.deal_quantity) as average_sell_prices,\n"
 * +
 * "sum(case when d.deal_type = 'B' then d.deal_amount else 0 end)/sum(d.deal_quantity) as average_buy_prices\n"
 * + "from " + dbname + "deal as d\n" + "inner join \n" +
 * "db_grad_cs_1917.instrument as i\n" +
 * "on d.deal_instrument_id = i.instrument_id\n" + "where d.deal_time between '"
 * + startDate + "' and '" + finishDate + "'\n" + "group by i.instrument_name";
 * //System.out.println(query); ResultSet rs = stmt.executeQuery(query);
 * while(rs.next()) { System.out.println(rs.getString(1) + " " + rs.getString(2)
 * + " " + rs.getString(3) + "\n"); } }
 * 
 * }
 */

// static private SQLQueries itsSelf = null;
// private Connection itsConnection;
// private String dbDriver ="com.mysql.jdbc.Driver"; // "com.mysql.jdbc.Driver";
// private String dbPath = "jdbc:mysql://10.0.75.1:3307/"; //
// "jdbc:mysql://52.209.91.145/";
// private String dbName = "db_grad"; // "db_grad_cs_1916";
// private String dbUser = "root"; // "selvyn";
// private String dbPwd = "ppp"; // "dbGradProg2016";
//
// static public SQLQueries getConnector() throws IOException
// {
// if( itsSelf == null )
// itsSelf = new SQLQueries();
// return itsSelf;
// }
// private SQLQueries(){}
//
// public Connection getConnection()
// {
// return itsConnection;
// }
//
// public boolean connect( Properties properties )
// {
// boolean result = false;
// try
// {
// MainUnit.log("On Entry -> SQLQueries.connect()");
//
// dbDriver = properties.getProperty("dbDriver");
// dbPath = properties.getProperty("dbPath");
// dbName = properties.getProperty("dbName");
// dbUser = properties.getProperty("dbUser");
// dbPwd = properties.getProperty("dbPwd");
//
// Class.forName( dbDriver );
//
// itsConnection = DriverManager.getConnection(dbPath + dbName,
// dbUser,
// dbPwd );
//
//
//
//
//
// MainUnit.log( itsConnection.getCatalog() );
//
// DatabaseMetaData metaInfo = itsConnection.getMetaData();
//
// // The following call returns a result set with following columns
// // TABLE_SCHEM String => schema name
// // TABLE_CATALOG String => catalog name (may be null)
// ResultSet rs = metaInfo.getSchemas();
//
// CatalogInfoIterator cursor = new CatalogInfoIterator( rs );
//
// while( cursor.next() )
// {
// System.out.println( cursor.getTable_Schema() + "/" +
// cursor.getTable_Catalog() );
// MainUnit.log( cursor.getTable_Schema() + "/" + cursor.getTable_Catalog() );
// MainUnit.log("Entered Loop");
// }
//
// rs.close();
//
// MainUnit.log( "Successfully connected to " + dbName );
//
// result = true;
// }
// catch( ClassNotFoundException | SQLException e )
// {
// e.printStackTrace();
//
//
// }
//
// MainUnit.log("On Exit -> DBConnector.connect()");
//
// return result;
// }
//
//
//
//// String itsConnection = DriverManager.getConnection(dbPath + dbName,
//// dbUser,
//// dbPwd );
//
/// *
// * private String dbDriver ="com.mysql.jdbc.Driver"; //
// "com.mysql.jdbc.Driver";
// * private String dbPath = "jdbc:mysql://10.0.75.1:3307/"; //
// * "jdbc:mysql://52.209.91.145/"; private String dbName = "db_grad"; //
// * "db_grad_cs_1916"; private String dbUser = "root"; // "selvyn"; private
// * String dbPwd = "ppp";
// */
// public static void main (String[] args) {
// try {
// Class.forName("com.mysql.cj.jdbc.Driver");
// }
// catch (ClassNotFoundException e) {
// // TODO Auto-generated catch block
// System.out.println("Driver unsuccessful");
// e.printStackTrace();
// }
//
//
// try {
// String url = "jdbc:msql://10.0.75.1:3307/db_grad";
// Connection conn = DriverManager.getConnection(url, "root", "ppp");
// Statement stmt = conn.createStatement();
// ResultSet rs;
//
// rs = stmt.executeQuery("SELECT user_id FROM users");
// while ( rs.next() ) {
// String user_id = rs.getString("user_id");
// System.out.println(user_id);
// }
// conn.close();
// }
// catch (Exception e){
// System.out.println("Unsuccessfull");
//
// e.printStackTrace();
// }
//
