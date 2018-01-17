package com.pukkaspice.data.release;

import java.io.File;
import java.util.logging.Logger;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import com.pukkaspice.data.DataSetup;

public class DataRelease {
    
    private static final Logger logger = Logger.getLogger(DataSetup.class.getName());
    
    private JdbcDatabaseTester  jdbcDatabaseTester;
    

    DataRelease(String driverClass, String connectionUrl, String username, String password) throws Exception {
        jdbcDatabaseTester = new JdbcDatabaseTester(driverClass, connectionUrl, username, password);
        jdbcDatabaseTester.setSetUpOperation(DatabaseOperation.REFRESH);
        jdbcDatabaseTester.setTearDownOperation(DatabaseOperation.NONE);

        File xmlFile = null;
        try {
            String xmlPath = ClassLoader.getSystemResource("com/pukkaspice/data/release/2016.1-release-data.xml").getFile();
            xmlFile = new File(xmlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        FlatXmlDataSet xmlDataSet = flatXmlDataSetBuilder.build(xmlFile);
        ReplacementDataSet dataSet = new ReplacementDataSet(xmlDataSet);
        dataSet.addReplacementObject("[NULL]", null);

        jdbcDatabaseTester.setDataSet(dataSet);
    }

    void applyChangeSet(String releaseTag) throws Exception {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(jdbcDatabaseTester.getConnection().getConnection()));
        Liquibase liquibase = new liquibase.Liquibase("com/pukkaspice/data/schema/changelog.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.tag(releaseTag);
        liquibase.update(new Contexts(), new LabelExpression());
    }
    
    public void refreshData() throws Exception {
        jdbcDatabaseTester.onSetup();
        jdbcDatabaseTester.onTearDown();
    }
    
    public static void main(String[] args) {
        
        // "rhc port-forward pukkaspice" is required before we run this (might have a too kill local mysql and any db connections first)
        
        logger.info("Starting data release...");
        
        try {
            DataRelease dataRelease = new DataRelease("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/pukkaspice", "adminEcMtHvE", "IgCLqbDlys5a");
            
            logger.info("Applying changeset...");    
            dataRelease.applyChangeSet("2016.2");
            
//            logger.info("Refreshing data from xml...");
//            dataRelease.refreshData();
            
            logger.info("Finshed data release.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
