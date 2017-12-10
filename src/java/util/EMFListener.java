package util;

import dataaccess.DBUtil;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author awarsyle
 */
public class EMFListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // application is starting up
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // application shutting down
        DBUtil.close();
    }
}
