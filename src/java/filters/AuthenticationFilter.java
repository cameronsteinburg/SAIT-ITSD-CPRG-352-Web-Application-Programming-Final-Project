package filters;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author awarsyle
 */
public class AuthenticationFilter implements Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();

        UserDB userdb = new UserDB();

        User user = new User();

        try {
            user = userdb.getUser((String) session.getAttribute("username"));
        } catch (NotesDBException ex) {
            ex.printStackTrace();
        }

        if (session.getAttribute("username") == null || user.getActive() == false) {
            ((HttpServletResponse) response).sendRedirect("login");
            return;
        }

        if (session.getAttribute("username") != null && user.getActive() && user.getRole().getRoleID() == 1) { //admin

            chain.doFilter(request, response);
            return;

        } else if (session.getAttribute("username") != null && user.getActive() && user.getRole().getRoleID() == 2) { //regular pleb

            ((HttpServletResponse) response).sendRedirect("notes");
            return;

            
        } else {

            ((HttpServletResponse) response).sendRedirect("companyAdmin");
            return;
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

}
