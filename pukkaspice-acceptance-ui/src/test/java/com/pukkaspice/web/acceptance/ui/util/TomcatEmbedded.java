package com.pukkaspice.web.acceptance.ui.util;

import java.io.File;

import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;

public class TomcatEmbedded {

    private static Tomcat mTomcat;
    private static String mWorkingDir = System.getProperty("java.io.tmpdir");
    private static String hostname = "localhost"; // this is the default anyways
    private static String appName = "app";

    public static void start() throws LifecycleException {
        mTomcat = new Tomcat();
        mTomcat.setPort(0);
        mTomcat.setBaseDir(mWorkingDir);
        mTomcat.setHostname(TomcatEmbedded.hostname); 

        Host host = mTomcat.getHost();
        host.setAppBase(mWorkingDir);
        host.setAutoDeploy(true);
        host.setDeployOnStartup(true);

        mTomcat.start();
    }
    
    public static boolean isTomcatRunning() {
        if (mTomcat != null) {
            return true;
        }
        return false;
    }

    public void deployAppWar() throws Throwable {
        File webApp = new File(mWorkingDir, TomcatEmbedded.appName);
        new ZipExporterImpl(createWebArchive()).exportTo(new File(mWorkingDir + "/" + TomcatEmbedded.appName + ".war"), true);
        mTomcat.addWebapp(mTomcat.getHost(), "/", webApp.getAbsolutePath());
    }

    private Archive<?> createWebArchive() {
        String workingDir = System.getProperty("user.dir");
        File f  = new File(workingDir + File.separator + "target" + File.separator + "dependency" );
        String warPath = null;
        for(File ft : f.listFiles()) {
            if (ft.getAbsolutePath().endsWith(".war")) {
                warPath  = ft.getAbsolutePath();
            }
        }
        return ShrinkWrap
                .create(ZipImporter.class, "pukkaspice.war")
                .importFrom(new File(warPath))
                .as(WebArchive.class);
    }

    public static void stop() throws Throwable {
        if (mTomcat.getServer() != null && mTomcat.getServer().getState() != LifecycleState.DESTROYED) {
            if (mTomcat.getServer().getState() != LifecycleState.STOPPED) {
                mTomcat.stop();
            }
            mTomcat.destroy();
        }
    }

    public static String getAppBaseURL() {
        if (TomcatEmbedded.isTomcatRunning()) {
            return "http://" + TomcatEmbedded.hostname + ":" + TomcatEmbedded.mTomcat.getConnector().getLocalPort() + "/";
        } else {
            return "http://localhost:8080/";
        }
    }
    
    public static int getTomcatPort() {
        return TomcatEmbedded.mTomcat.getConnector().getLocalPort();
    }

}
