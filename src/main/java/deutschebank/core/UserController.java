/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deutschebank.core;

import deutschebank.dbutils.User;
import deutschebank.dbutils.UserHandler;

/**
 *
 * @author Selvyn
 */
public class UserController
{
    final   private ApplicationScopeHelper ash = new ApplicationScopeHelper();

    public  String  verifyLoginDetails( String userId, String userPwd )
    {
        String result = null;
 
        User theUser = ash.userLogin(userId, userPwd);
                
        if( theUser != null)
            result = UserHandler.getLoader().toJSON(theUser);

        return result;
    }
}
