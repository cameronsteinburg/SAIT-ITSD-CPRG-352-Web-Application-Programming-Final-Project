package servlets;

import businesslogic.AccountService;
import businesslogic.UserService;
import dataaccess.DuplicateEmailException;
import dataaccess.UserDBException;
import domainmodel.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserService us = new UserService();
        String selectedUsername = (String) session.getAttribute("username");

        try {

            User user = us.get(selectedUsername);

            if (user.getActive() == false) {

                response.sendRedirect("login?logout");

            } else if (user.getActive() == true) {

                request.setAttribute("selectedUser", user);
                getServletContext().getRequestDispatcher("/WEB-INF/account/account.jsp").forward(request, response);
                return;
            }

        } catch (UserDBException ex) {

            ex.printStackTrace();
            throw new ServletException();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        boolean active = request.getParameter("active") != null;

        HttpSession session = request.getSession();
        UserService us = new UserService();

        String selectedUsername = (String) session.getAttribute("username");

        try {

            if (password == "" || email == "" || firstname == "" || lastname == "") {
                throw new IOException();
            }

            boolean unique = AccountService.isUnique(email);

            User seluser = us.get(selectedUsername);

            if (!email.equals(seluser.getEmail())) {
                if (unique == false) {
                    throw new DuplicateEmailException();
                }
            }

            User user = us.get(selectedUsername);
            us.update(selectedUsername, password, email, active, firstname, lastname, user.getCompany());

            User wutuser = us.get(selectedUsername);

            if (wutuser.getActive() == false) {

                response.sendRedirect("login?logout");

            } else if (wutuser.getActive() == true) {

                request.setAttribute("selectedUser", wutuser);
                request.setAttribute("themessage", "Account Succesfully Updated!");
                getServletContext().getRequestDispatcher("/WEB-INF/account/account.jsp").forward(request, response);
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            request.setAttribute("themessage", "Whoops.  Could not perform that action.");

        }

        try { //reload

            User user = us.get(selectedUsername);

            if (user.getActive() == false) {

                response.sendRedirect("login?logout");

            } else if (user.getActive() == true) {

                request.setAttribute("selectedUser", user);
                getServletContext().getRequestDispatcher("/WEB-INF/account/account.jsp").forward(request, response);
                return;
            }

        } catch (UserDBException ex) {

            ex.printStackTrace();
            throw new ServletException();
        }
    }
}
