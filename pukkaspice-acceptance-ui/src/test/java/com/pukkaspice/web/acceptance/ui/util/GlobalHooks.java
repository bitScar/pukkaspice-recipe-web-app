package com.pukkaspice.web.acceptance.ui.util;

import java.util.logging.Logger;

import cucumber.api.java.Before;

public class GlobalHooks {

    private Logger logger = Logger.getLogger(GlobalHooks.class.getName());
    
    private static int jvmCount = 0;
    
    private static TomcatEmbedded tomcatEmbedded;


    @Before(value="@web", order=0)
    public void beforeAll() throws Throwable {
      
        
        if (GlobalHooks.jvmCount == 0) {
            logger.info("Starting Tomcat and deploying application...");
            
            tomcatEmbedded = new TomcatEmbedded();
            TomcatEmbedded.start();
            tomcatEmbedded.deployAppWar();
            
            logger.info("Tomcat started on port: " + TomcatEmbedded.getTomcatPort());
        }
        
        GlobalHooks.jvmCount++;
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                GlobalHooks.jvmCount--;        
                
                if (GlobalHooks.jvmCount == 0) {
                    logger.info("Stopping Tomcat...");
                    
                    try {
                        TomcatEmbedded.stop();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
