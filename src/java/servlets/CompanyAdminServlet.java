package servlets;

import businesslogic.UserService;
import dataaccess.CompanyDB;
import dataaccess.UserDB;
import domainmodel.Company;
import domainmodel.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 734972
 */
public class CompanyAdminServlet extends HttpServlet {

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
                Logger.getLogger(CompanyAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("message", "Edit User Below");
        }

        List<Company> comps = null;
        CompanyDB cdb = new CompanyDB();
        comps = cdb.getAll();

        request.setAttribute("comps", comps);

        List<User> users = null;

        try {
            users = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(CompanyAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<User> mathcingUsers = new ArrayList<User>();
        HttpSession session = request.getSession();

        User curuser = (User) session.getAttribute("curuser");

        String thisCompany = curuser.getCompany().getCompanyName();
        request.setAttribute("thisCompany", thisCompany);

        int curuserCompanyID = curuser.getCompany().getCompanyID();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getCompany().getCompanyID() == curuserCompanyID) {
                mathcingUsers.add(users.get(i));
            }
        }

        request.setAttribute("users", mathcingUsers);

        getServletContext().getRequestDispatcher("/WEB-INF/admin/companyUsers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        HttpSession session = ((HttpServletRequest) request).getSession();
        User curuser = (User) session.getAttribute("curuser");
        
        request.setAttribute("thisCompany", curuser.getCompany().getCompanyName());
        
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

                            int id = newuser.getCompany().getCompanyID();
                            request.setAttribute("oldCompanyID", id);

                        } catch (Exception ex) {
                            Logger.getLogger(CompanyAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    List<User> users = null;
                    try {
                        users = us.getAll();

                    } catch (Exception ex) {
                        Logger.getLogger(CompanyAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ArrayList<User> mathcingUsers = new ArrayList<User>();
                    curuser = (User) session.getAttribute("curuser");
                    int curuserCompanyID = curuser.getCompany().getCompanyID();

                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getCompany().getCompanyID() == curuserCompanyID) {
                            mathcingUsers.add(users.get(i));
                        }
                    }

                    request.setAttribute("users", mathcingUsers);

                    getServletContext().getRequestDispatcher("/WEB-INF/admin/companyUsers.jsp").forward(request, response);
                    return;
                }

                us.delete(selectedUsername);
                request.setAttribute("message", "Account Successfully Deleted!");

            } else if (action.equals("edit")) {

                Company thisCompany = curuser.getCompany();
                us.update(username, password, email, active, firstname, lastname, thisCompany);
                request.setAttribute("message", "Account Successfully Updated!");

            } else if (action.equals("add")) {

                Company thisCompany = curuser.getCompany();
                us.insert(username, password, email, active, firstname, lastname, thisCompany);
                request.setAttribute("message", "Account Successfully Added!");
            }

        } catch (Exception ex) {
            request.setAttribute("message", "Whoops.  Could not perform that action.");
        }

        List<User> users = null;

        try {
            users = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(CompanyAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<User> mathcingUsers = new ArrayList<User>();
        
        int curuserCompanyID = curuser.getCompany().getCompanyID();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getCompany().getCompanyID() == curuserCompanyID) {
                mathcingUsers.add(users.get(i));
            }
        }

        request.setAttribute("users", mathcingUsers);

        getServletContext().getRequestDispatcher("/WEB-INF/admin/companyUsers.jsp").forward(request, response);
    }
}
