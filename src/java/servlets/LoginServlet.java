package servlets;

import businesslogic.AccountService;
import businesslogic.UserService;
import dataaccess.UserDBException;
import domainmodel.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author awarsyle
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("action") != null && request.getParameter("action").equals("resetPassword")) {
            
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            return;
        }

        if (request.getParameter("action") != null) {

            HttpSession session = request.getSession();
            session.invalidate();
            request.setAttribute("message", "You have successfully logged out!");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            request.setAttribute("message", "Invalid.  Please try again.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        AccountService as = new AccountService();

        try {
            if (as.loginHandler(username, password) != null) {

                session.setAttribute("username", username);

                UserService us = new UserService();
                User currentUser;

                try {
                    currentUser = us.get(username);

                    if (currentUser != null && currentUser.getActive() == true) {
                        session.setAttribute("curuser", currentUser);
                    }

                } catch (UserDBException ex) {

                    ex.printStackTrace();
                    request.setAttribute("message", "Invalid.  Please try again.");
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    return;
                }

                response.sendRedirect("admin");
                return;

            } else {

                request.setAttribute("message", "Invalid.  Please try again.");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
        } catch (UserDBException ex) {

            ex.printStackTrace();
            request.setAttribute("message", "Invalid.  Please try again.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

    }
}
