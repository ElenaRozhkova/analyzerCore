/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deutschebank.dbutils;

/**
 *
 * @author Selvyn
 */
public class Instrument
{
    private int     itsInstrumentID;
    private String  itsInstrumentName;
    
    public  Instrument( int id, String name )
    {
        itsInstrumentID = id;
        itsInstrumentName = name;
    }
    
    public int getInstrumentID()
    {
        return itsInstrumentID;
    }

    public void setInstrumentID(int itsInstrumentID)
    {
        this.itsInstrumentID = itsInstrumentID;
    }

    public String getInstrumentName()
    {
        return itsInstrumentName;
    }

    public void setInstrumentName(String itsInstrumentName)
    {
        this.itsInstrumentName = itsInstrumentName;
    }
}
