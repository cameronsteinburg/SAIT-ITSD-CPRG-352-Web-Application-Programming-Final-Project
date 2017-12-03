/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import domainmodel.Company;
import domainmodel.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author 734972
 */
public class CompanyDB {
    
    public List<Company> getAll()  {
        
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
    
}
