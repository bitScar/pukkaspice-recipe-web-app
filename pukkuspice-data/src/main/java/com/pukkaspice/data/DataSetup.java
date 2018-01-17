package com.pukkaspice.data;

import java.io.File;
import java.sql.Connection;
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

public class DataSetup {
    
    private static final Logger logger = Logger.getLogger(DataSetup.class.getName());
    
    private JdbcDatabaseTester  jdbcDatabaseTester;
    
    public DataSetup(String driverClass, String connectionUrl, String username, String password, boolean applySchemaChanges) throws Exception {
        jdbcDatabaseTester = new JdbcDatabaseTester(driverClass, connectionUrl, username, password);
        
        if (applySchemaChanges) {
            applyChangeSet(jdbcDatabaseTester.getConnection().getConnection());
        }
        
        jdbcDatabaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        jdbcDatabaseTester.setTearDownOperation(DatabaseOperation.NONE);
        
        File file = null;
        try {
            String file2 = ClassLoader.getSystemResource("com/pukkaspice/data/FullStandardXmlDataSet.xml").getFile();
//            String file2 = ClassLoader.getSystemResource("com/pukkaspice/data/release/2016.1-release-data.xml").getFile();
            file = new File(file2);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        
        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        FlatXmlDataSet standardDataSet = flatXmlDataSetBuilder.build(file);
        ReplacementDataSet dataSet = new ReplacementDataSet(standardDataSet); 
        dataSet.addReplacementObject("[NULL]", null);
        dataSet.addReplacementObject("[date+1]", "2020-01-01");
        
        jdbcDatabaseTester.setDataSet(dataSet);
    }
    
    public void doSetup() throws Exception {
        jdbcDatabaseTester.onSetup();
        jdbcDatabaseTester.onTearDown();
    }
    
    void applyChangeSet(Connection connection) throws Exception {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new liquibase.Liquibase("com/pukkaspice/data/schema/changelog.xml", new ClassLoaderResourceAccessor(), database);
//        liquibase.dropAll();
        liquibase.tag("2016.2");
        liquibase.update(new Contexts(), new LabelExpression());
    }

    public static void main(String[] args) {
        logger.info("Starting");
        try {
            DataSetup dataSetup = null;
            if (args.length > 0) {
                switch (args[0]) {
                case "mysql-local":
                    dataSetup = new DataSetup("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "", true);
                    break;
                default:
                    dataSetup = new DataSetup("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "", true);
                    break;
                }
            } else {
                dataSetup = new DataSetup("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "", true);
            }

            dataSetup.doSetup();
            
            
            logger.info("Finshed");
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
