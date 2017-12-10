package servlets;

import businesslogic.UserService;
import dataaccess.CompanyDB;
import dataaccess.CompanyDBException;
import dataaccess.UserDB;
import dataaccess.UserDBException;
import domainmodel.Company;
import domainmodel.Role;
import domainmodel.User;
import java.io.IOException;
import java.util.List;
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

            } catch (UserDBException ex) {

                ex.printStackTrace();
                throw new ServletException();
            }
            request.setAttribute("message", "Edit User Below");
        }

        request.setAttribute("roles", UserDB.getRoles());

        List<Company> comps = null;
        CompanyDB cdb = new CompanyDB();

        try {

            comps = cdb.getAll();

        } catch (CompanyDBException ex) {

            ex.printStackTrace();
            throw new ServletException();
        }

        request.setAttribute("comps", comps);

        List<User> users = null;

        try {

            users = us.getAll();

        } catch (UserDBException ex) {

            ex.printStackTrace();
            throw new ServletException();
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
                    request.setAttribute("message", "Whoops.  You can't delete yourself!");

                    action = request.getParameter("action");

                    if (action != null && action.equals("view")) {
                        selectedUsername = request.getParameter("selectedUsername");

                        try {

                            User newuser = us.get(selectedUsername);
                            request.setAttribute("selectedUser", user);

                            int id = newuser.getCompany().getCompanyID();
                            request.setAttribute("oldCompanyID", id);

                        } catch (UserDBException ex) {

                            ex.printStackTrace();
                            throw new ServletException();

                        }
                    }

                    List<User> users = null;
                    try {
                        users = us.getAll();

                    } catch (UserDBException ex) {

                        ex.printStackTrace();
                        throw new ServletException();

                    }
                    request.setAttribute("users", users);
                    getServletContext().getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);

                    return;
                }

                us.delete(selectedUsername);
                request.setAttribute("message", "Account Successfully Deleted!");

            } else if (action.equals("edit")) {

                String roleIDSTR = request.getParameter("selectRole");
                int roleID = Integer.parseInt(roleIDSTR);
                Role role = new Role(roleID);
                
                Company newCompany = CompanyDB.getCompanyFromIDString(request.getParameter("selectCompany"));
                us.update(username, password, email, active, firstname, lastname, newCompany, role);
                request.setAttribute("message", "Account Successfully Updated!");

            } else if (action.equals("add")) {
                String roleIDSTR = request.getParameter("selectRole");
                int roleID = Integer.parseInt(roleIDSTR);
                Role role = new Role(roleID);

                Company newCompany = CompanyDB.getCompanyFromIDString(request.getParameter("selectCompany"));
                us.insert(username, password, email, active, firstname, lastname, newCompany, role);
                request.setAttribute("message", "Account Successfully Added!");
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            request.setAttribute("message", "Whoops.  Could not perform that action.");
        }

        CompanyDB dbc = new CompanyDB();
        List<Company> comps = null;
        try {

            comps = dbc.getAll();

        } catch (CompanyDBException ex) {

            ex.printStackTrace();
            throw new ServletException();
        }

        request.setAttribute("comps", comps);

        List<User> users = null;

        try {

            users = us.getAll();

        } catch (UserDBException ex) {

            ex.printStackTrace();
            throw new ServletException();
        }

        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);
    }
}
