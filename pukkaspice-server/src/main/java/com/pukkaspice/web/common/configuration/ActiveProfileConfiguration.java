package com.pukkaspice.web.common.configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.core.env.AbstractEnvironment;

/**
 * This class sets the default Spring profile for the application when there are changes to the servlet context e.g. when the application is
 * started. It is registered with the servlet context in web.xml.
 * 
 * Using @ActiveProfiles("profile1, profile2")
 * with @RunWith(SpringJUnit4ClassRunner.class) in testing.
 * 
 * @author Chris Hatton
 */
public class ActiveProfileConfiguration implements ServletContextListener {   
    
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String dbHost = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        if (dbHost != null && !dbHost.trim().equals("")) {
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, 
                    ActiveProfileConfiguration.combineSpringProfiles(SpringProfile.DATABASE_PROD, SpringProfile.EMAIL_PROD, SpringProfile.IMAGE_STOREAGE_PROD));
        } else {
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, 
                    ActiveProfileConfiguration.combineSpringProfiles(SpringProfile.DATABASE_DEV, SpringProfile.EMAIL_DEV, SpringProfile.IMAGE_STOREAGE_DEV));
        }
    }
    
    public static String combineSpringProfiles(SpringProfile... profiles) {
        StringBuilder profileBuilder = new StringBuilder();
        for (int i = 0; i < profiles.length; i++) {
            profileBuilder.append(profiles[i].toString());
            if (i != profiles.length - 1) {
                profileBuilder.append(", ");
            }
        }
        return profileBuilder.toString();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) { }

}
