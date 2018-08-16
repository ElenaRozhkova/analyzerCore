package deutschebank.dbutils;

import java.sql.Timestamp;

public class Counterparty {
    private int     itsCounterpartyID;
    private String  itsCounterpartyName;
    private String 	itsCounterpartyStatus;
    private Timestamp 	itsCounterpartyDateRegistered;
    
    public  Counterparty( int id, String name, String status, Timestamp date )
    {
    	itsCounterpartyID = id;
    	itsCounterpartyName = name;
    	itsCounterpartyStatus = status;
    	itsCounterpartyDateRegistered = date;
    }

	public int getCounterpartyID() {
		return itsCounterpartyID;
	}

	public void setCounterpartyID(int CounterpartyID) {
		this.itsCounterpartyID = CounterpartyID;
	}

	public String getCounterpartyName() {
		return itsCounterpartyName;
	}

	public void setCounterpartyName(String CounterpartyName) {
		this.itsCounterpartyName = CounterpartyName;
	}

	public String getCounterpartyStatus() {
		return itsCounterpartyStatus;
	}

	public void setCounterpartyStatus(String CounterpartyStatus) {
		this.itsCounterpartyStatus = CounterpartyStatus;
	}

	public Timestamp getCounterpartyDateRegistered() {
		return itsCounterpartyDateRegistered;
	}

	public void setCounterpartyDateRegistered(Timestamp CounterpartyDateRegistered) {
		this.itsCounterpartyDateRegistered = CounterpartyDateRegistered;
	}
}
