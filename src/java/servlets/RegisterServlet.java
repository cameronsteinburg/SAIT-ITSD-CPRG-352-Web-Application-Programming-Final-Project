package servlets;

import businesslogic.AccountService;
import businesslogic.UserService;
import dataaccess.CompanyDB;
import dataaccess.CompanyDBException;
import dataaccess.DuplicateEmailException;
import dataaccess.UserDB;
import dataaccess.UserDBException;
import domainmodel.Company;
import domainmodel.User;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
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

        String uuid = (String) request.getParameter("uuid");

        if (uuid != null) {

            UserService us = new UserService();
            User user;

            try {

                user = us.getByUUID(uuid);
                user.setActive(true);
                user.setUUID(null);
                UserDB udb = new UserDB();
                udb.update(user);

                String url = request.getRequestURL().toString();
                String newemail = user.getEmail();
                String path = path = request.getServletContext().getRealPath("/WEB-INF/emailtemplates/welcomeuser.html");
                AccountService as = new AccountService();

                as.sendWelcome(newemail, url, path);

                request.setAttribute("message", "Your account has been activated! You can now log in");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

            } catch (UserDBException ex) {

                ex.printStackTrace();
                throw new ServletException();

            }
        }

        List<Company> comps = null;
        CompanyDB cdb = new CompanyDB();

        try {
            comps = cdb.getAll();

        } catch (CompanyDBException ex) {

            ex.printStackTrace();
            throw new ServletException();
        }

        String newuuid = request.getParameter("uuid");
        request.setAttribute("uuid", newuuid);

        request.setAttribute("comps", comps);

        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = (String) request.getParameter("action");

        if (action != null && action.equals("register")) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");

            try {

                boolean unique = AccountService.isUnique(email);

                if (unique == false) {

                    throw new DuplicateEmailException();
                }

                if (username == "" || password == "" || email == "" || firstname == "" || lastname == "") {
                    
                    throw new IOException();
                }

            } catch (Exception e) {

                e.printStackTrace();
                request.setAttribute("message", "Whoops.  Could not perform that action!");

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

            UserService us = new UserService();
            Company newCompany;

            try {

                String uuid = UUID.randomUUID().toString();

                newCompany = CompanyDB.getCompanyFromIDString(request.getParameter("selectCompany"));
                us.insert(username, password, email, false, firstname, lastname, newCompany, uuid);

                String url = request.getRequestURL().toString();

                AccountService as = new AccountService();

                String path = request.getServletContext().getRealPath("/WEB-INF/emailtemplates/registernewuser.html");

                as.sendActivationEmail(email, path, url, uuid);

                request.setAttribute("message", "Account Succesfully Created! A Confirmation e-mail has been sent to activate your account");

            } catch (UserDBException e) {

                e.printStackTrace();
                throw new ServletException();

            } catch (CompanyDBException r) {

                r.printStackTrace();
                throw new ServletException();
            }

            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

    }

}
