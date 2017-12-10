package businesslogic;

import dataaccess.CompanyDB;
import dataaccess.CompanyDBException;
import domainmodel.Company;
import java.util.List;

/**
 *
 * @author 734972
 */
public class CompanyService {
    
    private CompanyDB companyDB;

    public CompanyService() {
        companyDB = new CompanyDB();
    }

    public int update(String newName, int id) throws CompanyDBException {

        CompanyDB cdb = new CompanyDB();
        List<Company> companies = null;
        companies = cdb.getAll();
        
        Company company = null;

        for (int i = 0; i < companies.size(); i++) {
            
            if (companies.get(i).getCompanyID() == id) {
                company = companies.get(i);
            }
        }
        company.setCompanyName(newName);
        company.setCompanyID(id);
        return cdb.update(company);
    }

    public int insert(String newName, int id) throws CompanyDBException {
       CompanyDB cdb = new CompanyDB();
        List<Company> companies = null;
        companies = cdb.getAll();
        
        Company company = new Company(id, newName);

        return cdb.insert(company);
    }

}
