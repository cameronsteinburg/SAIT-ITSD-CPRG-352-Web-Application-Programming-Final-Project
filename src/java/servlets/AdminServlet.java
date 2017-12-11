package servlets;

import businesslogic.AccountService;
import businesslogic.NoteService;
import businesslogic.UserService;
import dataaccess.CompanyDB;
import dataaccess.CompanyDBException;
import dataaccess.DuplicateEmailException;
import dataaccess.UserDB;
import dataaccess.UserDBException;
import domainmodel.Company;
import domainmodel.Note;
import domainmodel.Role;
import domainmodel.User;
import java.io.IOException;
import java.util.ArrayList;
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
            
            HttpSession session = ((HttpServletRequest) request).getSession();
            User user = us.get((String) session.getAttribute("username")); //current user
            ArrayList<Note> publicNotes = NoteService.getPublicNotes(user);
            request.setAttribute("publicNotes", publicNotes);
            
            users = us.getAll();

        } catch (Exception ex) {

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
                String user = (String) session.getAttribute("username");
                //UserDB userdb = new UserDB();

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
                
                if (password == "" || email == "" || firstname == "" || lastname == "") {
                    throw new IOException();
                }

                boolean unique = AccountService.isUnique(email);

                User seluser = us.get(username);

                if (!email.equals(seluser.getEmail())) {
                    if (unique == false) {
                        throw new DuplicateEmailException();
                    }
                }

                String roleIDSTR = request.getParameter("selectRole");
                int roleID = Integer.parseInt(roleIDSTR);
                Role role = new Role(roleID);

                Company newCompany = CompanyDB.getCompanyFromIDString(request.getParameter("selectCompany"));
                us.update(username, password, email, active, firstname, lastname, newCompany, role);
                request.setAttribute("message", "Account Successfully Updated!");

            } else if (action.equals("add")) {
                
                if (password == "" || email == "" || firstname == "" || lastname == "") {
                    throw new IOException();
                }
                
                boolean unique = AccountService.isUnique(email);

                if (unique == false) {

                    throw new DuplicateEmailException();
                }

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
            
            
            User user = us.get((String) session.getAttribute("username")); 
            ArrayList<Note> publicNotes = NoteService.getPublicNotes(user);
            request.setAttribute("publicNotes", publicNotes);

        } catch (Exception ex) {

            ex.printStackTrace();
            throw new ServletException();
        }

        List<Role> roles = UserDB.getRoles();
        roles.remove(0);
        request.setAttribute("roles", roles);

        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);
    }
}
