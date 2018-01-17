package com.pukkaspice.data;

import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.logging.Logger;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

public class CopyOfDataSetup {
    
    private static final Logger logger = Logger.getLogger(CopyOfDataSetup.class.getName());
    
    private JdbcDatabaseTester jdbcDatabaseTester;
    
    public CopyOfDataSetup(String driverClass, String connectionUrl, String username, String password, boolean applySchemaChanges) throws Exception {
        jdbcDatabaseTester = new JdbcDatabaseTester(driverClass, connectionUrl, username, password);
        
        if (applySchemaChanges) {
            applyChangeSet(jdbcDatabaseTester.getConnection().getConnection());
        }
        
        jdbcDatabaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
//        jdbcDatabaseTester.setTearDownOperation(DatabaseOperation.NONE);
        
//        File file = new File(ClassLoader.getSystemResource("com/pukkaspice/data/StandardXmlDataSet.xml").getFile());
//        File file = new File(ClassLoader.getSystemResource("com//pukkaspice//data//FullStandardXmlDataSet.xml").getFile());
        
        String workingDir = System.getProperty("user.dir");
//        System.out.println();
        File file = new File(workingDir + "//src//main//resources//com//pukkaspice//data//FullStandardXmlDataSet.xml");
        
        
        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        FlatXmlDataSet standardDataSet = flatXmlDataSetBuilder.build(file);
        ReplacementDataSet dataSet = new ReplacementDataSet(standardDataSet); 
        dataSet.addReplacementObject("[NULL]", null);
        dataSet.addReplacementObject("[date+1]", "2020-01-01");
        
        jdbcDatabaseTester.setDataSet(dataSet);
    }
    
    public void doSetup() throws Exception {
        jdbcDatabaseTester.onSetup();
//        jdbcDatabaseTester.onTearDown();
    }
    
    void applyChangeSet(Connection connection) throws Exception {
        String workingDir = System.getProperty("user.dir");
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new liquibase.Liquibase(workingDir + "//src//main//resources//com//pukkaspice//data//schema//changelog.xml", new FileSystemResourceAccessor(), database);
        
        liquibase.dropAll();
        liquibase.tag("1.0.0-BUILD-SNAPSHOT");
        liquibase.update(new Contexts(), new LabelExpression());
    }

    public static void main(String[] args) {
        logger.info("Starting");
        try {
            CopyOfDataSetup dataSetup = null;
            if (args.length > 0) {
                switch (args[0]) {
                case "mysql-local":
                    dataSetup = new CopyOfDataSetup("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "", true);
                    break;
                default:
                    dataSetup = new CopyOfDataSetup("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "", true);
                    break;
                }
            } else {
                dataSetup = new CopyOfDataSetup("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "", true);
            }

            dataSetup.doSetup();
            
            
            logger.info("Finshed");
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
