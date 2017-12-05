package servlets;

import dataaccess.CompanyDB;
import domainmodel.Company;
import java.io.IOException;
import java.util.List;
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
        List<Company> companies = cdb.getAll();
        request.setAttribute("companies", companies);
        getServletContext().getRequestDispatcher("/WEB-INF/admin/companies.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
