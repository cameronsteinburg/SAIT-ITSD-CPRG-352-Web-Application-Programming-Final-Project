package servlets;

import businesslogic.AccountService;
import businesslogic.UserService;
import dataaccess.UserDBException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 734972
 */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uuid = request.getParameter("uuid");
        request.setAttribute("uuid", uuid);

        getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = (String) request.getParameter("action");

        if (action != null && action.equals("newPassword")) {

            String uuid = (String) request.getParameter("uuid");
            String password = (String) request.getParameter("password");

            UserService us = new UserService();
            String username;
            
            try {
                username = us.getByUUID(uuid).getUUID();

                AccountService as = new AccountService();
                as.changePassword(username, password);

                request.setAttribute("errormessage", "Password Successfully Reset!");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;

            } catch (UserDBException ex) {

                ex.printStackTrace();
                throw new ServletException();

            }
        }

        String url = request.getRequestURL().toString();
        String email = (String) request.getParameter("email");

        AccountService as = new AccountService();

        String path = request.getServletContext().getRealPath("/WEB-INF/emailtemplates/resetpassword.html");

        try {

            int result = as.resetPassword(email, path, url);

            if (result == 1) {

                request.setAttribute("message", "E-mail Sent!");
            } else {

                request.setAttribute("message", "Whoops. Could not perform that action");
            }

        } catch (UserDBException ex) {

            ex.printStackTrace();
            throw new ServletException();

        }

        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }
}
