package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.Company;
import domainmodel.Role;
import domainmodel.User;
import java.util.List;

public class UserService {

    private UserDB userDB;

    public UserService() {
        userDB = new UserDB();
    }

    public User get(String username) throws NotesDBException {
        return userDB.getUser(username);
    }

    public List<User> getAll() throws NotesDBException {
        return userDB.getAll();
    }

    public int update(String username, String password, String email, boolean active, String firstname, String lastname, Company company) throws Exception {
        User user = userDB.getUser(username);
        user.setPassword(password);
        user.setActive(active);
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setCompany(company);
        return userDB.update(user);
    }
 

    public int delete(String username) throws NotesDBException  {
        User deletedUser = userDB.getUser(username);
        return userDB.delete(deletedUser);
    }

    public int insert(String username, String password, String email, boolean active, String firstname, String lastname, Company company) throws Exception {
        User user = new User(username, password, email, active, firstname, lastname, company);
        Role role = new Role(2);  // default regular user role
        user.setRole(role);
        return userDB.insert(user);
    }
    
    public User getByUUID(String uuid) {
        return userDB.getByUUID(uuid);
    }
    
    public User getByEmail(String email) {
        return userDB.getByEmail(email);
    }
}
