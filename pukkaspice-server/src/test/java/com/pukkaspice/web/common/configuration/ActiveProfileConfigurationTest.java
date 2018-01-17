package com.pukkaspice.web.common.configuration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import javax.servlet.ServletContextEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ClearSystemProperties;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.springframework.core.env.AbstractEnvironment;

public class ActiveProfileConfigurationTest {
    
    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();
    
    @Rule
    public final ClearSystemProperties myPropertyIsCleared = new ClearSystemProperties(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
    
    private ActiveProfileConfiguration activeProfileConfiguration;
    
    
    @Before
    public void setUp() {
        activeProfileConfiguration = new ActiveProfileConfiguration();
    }
    
    @Test
    public void should_return_blank_profile_string_when_no_profiles_added() {
        String combineSpringProfiles = ActiveProfileConfiguration.combineSpringProfiles();
        
        assertEquals("Expected blank string for Spring profiles.", "", combineSpringProfiles);
    }
    
    @Test
    public void should_return_profile_string_with_one_profile_when_one_profile_passed() {
        String profileString = ActiveProfileConfiguration.combineSpringProfiles(SpringProfile.DATABASE_DEV);
        
        assertEquals("Expected one profile in profile string.", SpringProfile.DATABASE_DEV.toString(), profileString);
    }
    
    @Test
    public void should_return_profile_string_with_two_profile_when_two_profiles_passed() {
        String profileString = ActiveProfileConfiguration.combineSpringProfiles(SpringProfile.DATABASE_DEV, SpringProfile.EMAIL_DEV);
        
        String expectedProfileString = SpringProfile.DATABASE_DEV.toString() + ", " + SpringProfile.EMAIL_DEV.toString();
        
        assertEquals("Expected two profiles in profile string.", expectedProfileString, profileString);
    }
    
    @Test
    public void should_use_prod_profile_when_openshift_mysql_enviroment_variable_set() {
        
        // GIVEN
        environmentVariables.set("OPENSHIFT_MYSQL_DB_HOST", "SomeTestValue");
        
        // WHEN
        ServletContextEvent servletContextEvent = mock(ServletContextEvent.class);
        activeProfileConfiguration.contextInitialized(servletContextEvent);
        
        String profileString = ActiveProfileConfiguration.combineSpringProfiles(SpringProfile.DATABASE_PROD, SpringProfile.EMAIL_PROD, SpringProfile.IMAGE_STOREAGE_PROD);
        
        // THEN
        assertEquals(profileString, System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));
    }
    
    @Test
    public void should_use_dev_profile_when_openshift_mysql_enviroment_variable_not_set() {
        // GIVEN
        // That no environment variables have been set
        
        // WHEN
        ServletContextEvent servletContextEvent = mock(ServletContextEvent.class);
        activeProfileConfiguration.contextInitialized(servletContextEvent);
        
        String profileString = ActiveProfileConfiguration.combineSpringProfiles(SpringProfile.DATABASE_DEV, SpringProfile.EMAIL_DEV, SpringProfile.IMAGE_STOREAGE_DEV);
        
        // THEN
        assertEquals(profileString, System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));
        
    }

}
