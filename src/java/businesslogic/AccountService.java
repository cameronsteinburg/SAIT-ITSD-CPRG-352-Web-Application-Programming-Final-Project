package businesslogic;

import dataaccess.UserDB;
import domainmodel.User;

/**
 *
 * @author awarsyle
 */
public class AccountService {
    
    public User loginHandler(String username, String password) {
        
        UserDB userDB = new UserDB();
        
        try {
            
            User user = userDB.getUser(username);
            
            if (user.getPassword().equals(password)) {
                return user;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
}
