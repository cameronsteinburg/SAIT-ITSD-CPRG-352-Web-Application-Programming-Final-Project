package dataaccess;

import domainmodel.Company;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author 734972
 */
public class CompanyDB {

    public List<Company> getAll() {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Company> comps = em.createNamedQuery("Company.findAll", Company.class).getResultList();
            return comps;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot read companies", ex);
        } finally {
            em.close();
        }
        return null;
    }

    public static Company getCompanyFromIDString(String id) {

        int companyID = -1;

        try {
            companyID = Integer.parseInt(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CompanyDB cdb = new CompanyDB();
        List<Company> comps = cdb.getAll();
        Company newCompany = null;

        for (int i = 0; i < comps.size(); i++) {
            if (companyID == comps.get(i).getCompanyID()) {
                newCompany = comps.get(i);
            }
        }

        return newCompany;
    }

    public int update(Company company) throws Exception {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(company);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot update " + company.toString(), ex);
            throw new Exception("Error updating user");
        } finally {
            em.close();
        }
    }

    public int insert(Company company) throws Exception {
        
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(company);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot insert " + company.toString(), ex);
            throw new Exception("Error inserting company");
        } finally {
            em.close();
        }
    }

}
