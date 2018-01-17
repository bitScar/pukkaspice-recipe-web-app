package com.pukkaspice.data;

import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class CreateFullDataSet {
    
    private static final Logger logger = Logger.getLogger(CreateFullDataSet.class.getName());
    
    private JdbcDatabaseTester jdbcDatabaseTester;
    
    public CreateFullDataSet(String driverClass, String connectionUrl, String username, String password) throws Exception {
        jdbcDatabaseTester = new JdbcDatabaseTester(driverClass, connectionUrl, username, password);
    }
    
    void doSetup() throws Exception {
        IDatabaseConnection connection = jdbcDatabaseTester.getConnection();
        IDataSet fullDataSet = connection.createDataSet();
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("src/main/resources/com/pukkaspice/data/FullStandardXmlDataSet2.xml"));
    }

    public static void main(String[] args) {
        logger.info("Starting");
        try {
            CreateFullDataSet dataSetup = null;
            if (args.length > 0) {
                switch (args[0]) {
                case "mysql-local":
                    dataSetup = new CreateFullDataSet("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "");
                    break;
                default:
                    dataSetup = new CreateFullDataSet("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "");
                    break;
                }
            } else {
                dataSetup = new CreateFullDataSet("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "");
            }

            dataSetup.doSetup();
            logger.info("Finshed");
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
