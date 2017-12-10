package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.mail.MessagingException;

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

    public boolean changePassword(String uuid, String password) {
        UserDB db = new UserDB();
        try {
            User user = db.getByUUID(uuid);
            user.setPassword(password);
            user.setUUID(null);
            UserDB ur = new UserDB();
            ur.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public int resetPassword(String email, String path, String url) throws NotesDBException {

        String uuid = UUID.randomUUID().toString();
        UserDB db = new UserDB();
        UserService us = new UserService();
        User user = us.getByEmail(email);

        if (user != null) {
   
            user.setUUID(uuid);
            db.update(user);

            String link = url + "?uuid=" + uuid;
            try {
                HashMap<String, String> contents = new HashMap<>();
                contents.put("firstname", user.getFirstname());
                contents.put("lastname", user.getLastname());
                contents.put("username", user.getUsername());
                contents.put("link", link);

                try {
                    WebMailService.sendMail(email, "Password Reset Request", path, contents);
                } catch (IOException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (MessagingException ex) {
                Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                return 2;
            } catch (NamingException ex) {
                Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                return 2;
            }

            return 1;

        } else {
            return 2;
        }
    }

}
