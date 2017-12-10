package filters;

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
 * @author 734972
 */
public class ResetFilter implements Filter {

    private FilterConfig filterConfig = null;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        UserDB userdb = new UserDB();
        User user = new User();
        String uuid = (String) request.getParameter("uuid");

        if (session.getAttribute("username") == null) {

            chain.doFilter(request, response);
            return;

        } else if (uuid != null) {

            chain.doFilter(request, response);
            return;

        } else {
            ((HttpServletResponse) response).sendRedirect("notes");
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
