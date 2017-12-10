package dataaccess;

import domainmodel.Role;
import domainmodel.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDB {

    public int insert(User user) throws UserDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {

            trans.begin();
            em.persist(user);
            trans.commit();
            return 1;

        } catch (Exception ex) {

            ex.printStackTrace();
            trans.rollback();
            throw new UserDBException();

        } finally {
            em.close();
        }
    }

    public int update(User user) throws UserDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {

            trans.begin();
            em.merge(user);
            trans.commit();
            return 1;

        } catch (Exception ex) {

            ex.printStackTrace();
            trans.rollback();
            throw new UserDBException();

        } finally {
            em.close();
        }
    }

    public List<User> getAll() throws UserDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {

            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;

        } catch (Exception ex) {

            ex.printStackTrace();
            throw new UserDBException();

        } finally {
            em.close();
        }
    }


    public User getUser(String username) throws UserDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {

            User user = em.find(User.class, username);
            return user;

        } catch (Exception ex) {

            ex.printStackTrace();
            throw new UserDBException();

        } finally {
            em.close();
        }
    }

    public int delete(User user) throws UserDBException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {

            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
            return 1;

        } catch (Exception ex) {

            ex.printStackTrace();
            trans.rollback();
            throw new UserDBException();

        } finally {
            em.close();
        }
    }

    public User getByUUID(String uuid) throws UserDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {

            User user = em.createNamedQuery("User.findByUUID", User.class).setParameter("UUID", uuid).getSingleResult();
           // em.close();
            return user;

        } catch (Exception ex) {

            ex.printStackTrace();
            throw new UserDBException();

        } finally {

            em.close();
        }

    }

    public User getByEmail(String email) throws UserDBException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {

            User user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
          //  em.close();
            return user;

        } catch (Exception ex) {

            ex.printStackTrace();
            throw new UserDBException();

        } finally {

            em.close();
        }
    }
    
    public static ArrayList<Role> getRoles(){
        
        Role admin = new Role(1, "System Admin");
        Role user = new Role(2, "Regular User");
        Role companyAdmin = new Role(3, "Company Admin");
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(admin);
        roles.add(user);
        roles.add(companyAdmin);
        
        return roles;
    }
}
