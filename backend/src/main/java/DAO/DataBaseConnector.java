package DAO;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnector {

        private Connection conn;

        private void connect() {
            try{Class.forName("jdbc:postgresql");}catch(Exception e){e.printStackTrace();}
            // SQLite connection string
            String url = "jdbc:postgresql:/home/rageoverkill/wildwildwest/tw01qs-frontend/questStore/backend/src/main/resources/questStoreDB";
            try {
                this.conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public ResultSet query(String sql){

            try {
                this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql);
//            this.conn.close();

                return rs;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
}
