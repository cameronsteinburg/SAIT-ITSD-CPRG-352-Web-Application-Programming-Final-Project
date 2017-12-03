package servlets;

import businesslogic.UserService;
import dataaccess.CompanyDB;
import dataaccess.UserDB;
import domainmodel.Company;
import domainmodel.User;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService us = new UserService();

        String action = request.getParameter("action");

        if (action != null && action.equals("view")) {
            String selectedUsername = request.getParameter("selectedUsername");

            try {

                User user = us.get(selectedUsername);

                request.setAttribute("selectedUser", user);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<User> users = null;
        try {
            users = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = ((HttpServletRequest) request).getSession();

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        boolean active = request.getParameter("active") != null;

        UserService us = new UserService();

        try {
            if (action.equals("delete")) {

                String selectedUsername = request.getParameter("selectedUsername");

                UserDB userdb = new UserDB();

                String user = (String) session.getAttribute("username");

                if (selectedUsername.equals(user)) {
                    //you cant delete yourself!
                    request.setAttribute("message", "Whoops.  You can't delete yourself.");

                    action = request.getParameter("action");

                    if (action != null && action.equals("view")) {
                        selectedUsername = request.getParameter("selectedUsername");
                        try {

                            User newuser = us.get(selectedUsername);

                            request.setAttribute("selectedUser", user);
                        } catch (Exception ex) {
                            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    List<User> users = null;
                    try {
                        users = us.getAll();
                    } catch (Exception ex) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.setAttribute("users", users);
                    getServletContext().getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);

                    return;
                }

                us.delete(selectedUsername);
                request.setAttribute("message", "Account Successfully Deleted!");

            } else if (action.equals("edit")) {
                
                Company newCompany = CompanyDB.getCompanyFromIDString(request.getParameter("selectCompany"));
                
                us.update(username, password, email, active, firstname, lastname, newCompany);
                request.setAttribute("message", "Account Successfully Updated!");

            } else if (action.equals("add")) {
                
               Company newCompany = CompanyDB.getCompanyFromIDString(request.getParameter("selectCompany"));
                
                us.insert(username, password, email, active, firstname, lastname, newCompany);
                request.setAttribute("message", "Account Successfully Added!");
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Whoops.  Could not perform that action.");
        }

        List<User> users = null;
        try {
            users = us.getAll();

            int number_notes = users.get(0).getNoteCollection().size();

        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);
        //return;
    }
}
