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
public class User
{
    private String  itsUserID;
    private String  itsUserPwd;
    
    public  User( String userid, String pwd )
    {
        itsUserID = userid;
        itsUserPwd = pwd;
    }

    public String getUserID()
    {
        return itsUserID;
    }

    public void setUserID(String itsUserID)
    {
        this.itsUserID = itsUserID;
    }

    public String getUserPwd()
    {
        return itsUserPwd;
    }

    public void setUserPwd(String itsUserPwd)
    {
        this.itsUserPwd = itsUserPwd;
    }
}
