package servlets;

import businesslogic.UserService;
import dataaccess.CompanyDB;
import dataaccess.CompanyDBException;
import dataaccess.UserDBException;
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
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Company> comps = null;
        CompanyDB cdb = new CompanyDB();

        try {
            comps = cdb.getAll();
            
        } catch (CompanyDBException ex) {

            ex.printStackTrace();
            throw new ServletException();
        }

        request.setAttribute("comps", comps);

        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        UserService us = new UserService();
        Company newCompany;
        try {

            newCompany = CompanyDB.getCompanyFromIDString(request.getParameter("selectCompany"));

            us.insert(username, password, email, true, firstname, lastname, newCompany);

        } catch (UserDBException e) {

            e.printStackTrace();
            throw new ServletException();

        } catch (CompanyDBException r) {
            
            r.printStackTrace();
            throw new ServletException();
        }

        request.setAttribute("message", "Account Succesfully Created!");
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
