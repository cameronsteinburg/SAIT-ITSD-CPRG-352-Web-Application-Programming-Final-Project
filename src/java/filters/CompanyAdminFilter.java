/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author 734972
 */
public class CompanyAdminFilter implements Filter{
    
        private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       
       HttpSession session = ((HttpServletRequest) request).getSession();
        
        UserDB userdb = new UserDB();

        User user = new User();
        
        try {
            user = userdb.getUser((String) session.getAttribute("username"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (session.getAttribute("username") != null && user.getActive() && user.getRole().getRoleID() != 1 && user.getRole().getRoleID() != 2) { 

            chain.doFilter(request, response);
            return;
            
        } else{
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
