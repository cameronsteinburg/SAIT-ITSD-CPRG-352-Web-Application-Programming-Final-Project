package dataaccess;

import domainmodel.Company;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author 734972
 */

public class CompanyDB {

    public List<Company> getAll() throws CompanyDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Company> comps = em.createNamedQuery("Company.findAll", Company.class).getResultList();
            return comps;
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            throw new CompanyDBException();
            
        } finally {
            em.close();
        }
    }

    public static Company getCompanyFromIDString(String id) throws CompanyDBException {

        int companyID = -1;

        try {
            companyID = Integer.parseInt(id);
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new CompanyDBException();
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

    public int update(Company company) throws CompanyDBException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            
            trans.begin();
            em.merge(company);
            trans.commit();
            return 1;
            
        } catch (Exception ex) {
            
            trans.rollback();
            ex.printStackTrace();
            throw new CompanyDBException();
            
        } finally {
            em.close();
        }
    }

    public int insert(Company company) throws CompanyDBException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            
            trans.begin();
            em.persist(company);
            trans.commit();
            return 1;
            
        } catch (Exception ex) {
            
            trans.rollback();
            throw new CompanyDBException();
            
        } finally {
            em.close();
        }
    }

}
