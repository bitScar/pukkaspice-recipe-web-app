package com.pukkaspice.data.release;

import java.io.FileOutputStream;
import java.util.logging.Logger;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import com.pukkaspice.data.DataSetup;

public class BackupDatabase {
    
    private static final Logger logger = Logger.getLogger(DataSetup.class.getName());

    private JdbcDatabaseTester jdbcDatabaseTester;

    public BackupDatabase(String driverClass, String connectionUrl, String username, String password) throws Exception {
        jdbcDatabaseTester = new JdbcDatabaseTester(driverClass, connectionUrl, username, password);
    }

    void backup() throws Exception {
        IDatabaseConnection connection = jdbcDatabaseTester.getConnection();
        IDataSet fullDataSet = connection.createDataSet();
        
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("src/main/resources/com/pukkaspice/data/release/backup-20160626.xml"));
    }

    public static void main(String[] args) {

        // "rhc port-forward pukkaspice" is required before we run this

        logger.info("Starting data backup...");

        try {
            BackupDatabase dataRelease = new BackupDatabase("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/pukkaspice", "adminypyBsGu", "wQw3kUL_k5c-");
            dataRelease.backup();

            logger.info("Finshed data backup.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
