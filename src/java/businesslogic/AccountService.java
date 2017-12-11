package businesslogic;

import dataaccess.UserDB;
import dataaccess.UserDBException;
import domainmodel.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.naming.NamingException;
import javax.mail.MessagingException;

/**
 *
 * @author awarsyle
 */
public class AccountService {

    public User loginHandler(String username, String password) throws UserDBException {

        UserDB userDB = new UserDB();

        try {

            User user = userDB.getUser(username);

            try {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            } catch (NullPointerException e) {

                e.printStackTrace();
                throw new UserDBException();
            }

        } catch (UserDBException e) {

            e.printStackTrace();
            throw new UserDBException();

        } catch (NullPointerException e) {

            e.printStackTrace();
            throw new UserDBException();
        }

        return null;
    }

    public void changePassword(String uuid, String password) throws UserDBException {
        UserDB db = new UserDB();
        try {
            User user = db.getByUUID(uuid);
            user.setPassword(password);
            user.setUUID(null);
            UserDB ur = new UserDB();
            ur.update(user);

        } catch (UserDBException ex) {

            throw new UserDBException();
        }
    }

    public int resetPassword(String email, String path, String url) throws UserDBException, IOException {

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

                    ex.printStackTrace();
                    throw new IOException();
                }

            } catch (MessagingException ex) {

                ex.printStackTrace();
                return 2;

            } catch (NamingException ex) {

                ex.printStackTrace();
                return 2;
            }

            return 1;

        } else {
            return 2;
        }
    }

    public int sendActivationEmail(String email, String path, String url, String uuid) throws UserDBException, IOException {

        UserService us = new UserService();
        User user = us.getByEmail(email);

        String link = url + "?uuid=" + uuid;
        user.setUUID(uuid);

        try {
            HashMap<String, String> contents = new HashMap<>();
            contents.put("firstname", user.getFirstname());
            contents.put("lastname", user.getLastname());
            contents.put("username", user.getUsername());
            contents.put("link", link);

            try {

                WebMailService.sendMail(email, "NotesKeepr Activation", path, contents);

            } catch (IOException ex) {

                ex.printStackTrace();
                throw new IOException();
            }

        } catch (MessagingException ex) {

            ex.printStackTrace();
            return 2;

        } catch (NamingException ex) {

            ex.printStackTrace();
            return 2;
        }

        return 1;
    }

    public int sendWelcome(String email, String url, String path) throws UserDBException, IOException {

        UserService us = new UserService();
        User user = us.getByEmail(email);

        try {
            HashMap<String, String> contents = new HashMap<>();
            contents.put("firstname", user.getFirstname());
            contents.put("lastname", user.getLastname());
            contents.put("username", user.getUsername());

            try {

                WebMailService.sendMail(email, "Welcome!", path, contents);

            } catch (IOException ex) {

                ex.printStackTrace();
                throw new IOException();
            }

        } catch (MessagingException ex) {

            ex.printStackTrace();
            return 2;

        } catch (NamingException ex) {

            ex.printStackTrace();
            return 2;
        }

        return 1;
    }
    
    public static boolean isUnique(String email) throws UserDBException{
    
        ArrayList<String> emails = new ArrayList<String>();
        UserService us = new UserService();
        List<User> users = us.getAll();
        
        for(int i = 0; i < users.size();i++){
            emails.add(users.get(i).getEmail());
        }
        
        for(int i = 0; i < emails.size(); i++){
            if(email.equals(emails.get(i))){
                return false; //match found
            }
        }
        
        return true; //no match found
    }

}
