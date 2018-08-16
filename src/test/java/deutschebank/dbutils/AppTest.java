package deutschebank.dbutils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	@Test
    public void checkConnectionToDatabaseSuccessful()
	{
			//assert(DBConnector.)
	}
	
	@Test
    public void checkDatabaseNameConnectedToIsCorrect()
	{
		//String dbname = DBConnnector.
	}

	@Test
	public void incorrectLoginGetsRejected() {

	}

	@Test
	public void avgPriceBoughtForInstrumentIsCorrect() {
		String test = SQLQueries.avgPriceBought(1);
		System.out.println(test);
		//assertTrue(test.contains("1298.07"));
		//assertTrue(test.contains("1298"));

	}

	@Test
	public void instrumentSelectedShowsCorrectData() {
	}

	@Test
	public void instrumentAveragePriceIsCorrect() {

	}

	@Test
	public void rawDataIsDisplayedInThreeNF() {

	}

	@Test
	public void counterPartySelectedShowsCorrectData() {

	}

	@Test
	public void stockPriceDifferenceToPreviousValueIsCorrect() {

	}

}
