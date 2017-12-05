package servlets;

import businesslogic.CompanyService;
import dataaccess.CompanyDB;
import domainmodel.Company;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 734972
 */
public class CompaniesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CompanyDB cdb = new CompanyDB();
        List<Company> startingComps = null;
        startingComps = cdb.getAll();
        int newID = startingComps.size() + 1;
        request.setAttribute("newID", newID);

        int currentTopID = startingComps.size();
        request.setAttribute("currentTopID", currentTopID);

        List<Company> companies = cdb.getAll();
        request.setAttribute("companies", companies);

        String action = request.getParameter("action");
        Company selectedCompany = null;

        if (action != null && action.equals("view")) {

            String selectedCompanyIDString = request.getParameter("selectedCompanyID");

            int selectedCompanyID = Integer.parseInt(selectedCompanyIDString);

            for (int i = 0; i < companies.size(); i++) {

                if (companies.get(i).getCompanyID() == selectedCompanyID) {

                    selectedCompany = companies.get(i);
                    request.setAttribute("selectedCompany", selectedCompany);
                    request.setAttribute("selectedCompanyName", selectedCompany.getCompanyName());
                }
            }

            request.setAttribute("message", "Edit Company Below");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/admin/companies.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CompanyDB cdb = new CompanyDB();
        CompanyService cs = new CompanyService();
        List<Company> currentCompanies = cdb.getAll();

        String action = request.getParameter("action");
        String newName = request.getParameter("companyname");

        
        int newID = currentCompanies.size() + 1;
        
        try {

            if (action.equals("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                cs.update(newName, id);
                request.setAttribute("message", "Company Successfully Updated!");
                
            } else if (action.equals("add")) {

              cs.insert(newName, newID);
              request.setAttribute("message", "Company Successfully Added!");
            }

        } catch (Exception e) {
            request.setAttribute("message", "Whoops.  Could not perform that action.");
        }

        List<Company> companies = cdb.getAll();
        request.setAttribute("companies", companies);

        newID = companies.size() + 1;
        request.setAttribute("newID", newID);

        int currentID = companies.size();
        request.setAttribute("currentTopID", currentID);

        getServletContext().getRequestDispatcher("/WEB-INF/admin/companies.jsp").forward(request, response);
    }
}
