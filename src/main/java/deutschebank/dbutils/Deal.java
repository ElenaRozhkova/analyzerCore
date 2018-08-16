package deutschebank.dbutils;

public class Deal {
    private int     itsDealID;
    private String	itsDealTime;
    private int 	itsDealCounterpartyID;
    private int		itsDealInstrumetID;
    private String	itsDealType;
    private float	itsDealAmount;
    private int		itsDealQuantity;
    
    
    public  Deal( int id, String time, int counterpartyID, int instrumentID, String type, float amount, int quantity  )
    {
        itsDealID = id;
        itsDealTime = time;
        itsDealCounterpartyID = counterpartyID;
        itsDealInstrumetID = instrumentID;
        itsDealType = type;
        itsDealAmount = amount;
        itsDealQuantity = quantity;
    }
    
  
    public int getDealID() {
		return itsDealID;
	}


	public void setDealID(int itsDealID) {
		this.itsDealID = itsDealID;
	}


	public String getDealTime() {
		return itsDealTime;
	}


	public void setDealTime(String itsDealTime) {
		this.itsDealTime = itsDealTime;
	}


	public int getDealCounterpartyID() {
		return itsDealCounterpartyID;
	}


	public void setDealCounterpartyID(int itsDealCounterpartyID) {
		this.itsDealCounterpartyID = itsDealCounterpartyID;
	}


	public int getDealInstrumetID() {
		return itsDealInstrumetID;
	}


	public void setDealInstrumetID(int itsDealInstrumetID) {
		this.itsDealInstrumetID = itsDealInstrumetID;
	}


	public String getDealType() {
		return itsDealType;
	}


	public void setDealType(String itsDealType) {
		this.itsDealType = itsDealType;
	}


	public float getDealAmount() {
		return itsDealAmount;
	}


	public void setDealAmount(float itsDealAmount) {
		this.itsDealAmount = itsDealAmount;
	}


	public int getDealQuantity() {
		return itsDealQuantity;
	}


	public void setDealQuantity(int itsDealQuantity) {
		this.itsDealQuantity = itsDealQuantity;
	}
	
}
