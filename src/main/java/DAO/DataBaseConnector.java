package DAO;

import java.security.InvalidParameterException;
import java.sql.*;

public class DataBaseConnector {

        private Connection conn;

        public Connection connect() {
            String url = "jdbc:postgresql://localhost:5432/questStoreDB";
//            String url = "jdbc:postgresql://localhost:5432/queststoredb";
            try {
                this.conn = DriverManager.getConnection(url , "postgres", "123");
//                this.conn = DriverManager.getConnection(url , "rageoverkill", "dupa123");
                this.conn.setAutoCommit(false);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return conn;
        }

        public void disconnect() throws SQLException{
            this.conn.close();
        }

        public ResultSet query(String sql, String[] attr) throws SQLException{
            this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            if(sql.split("\\?").length - 1 != attr.length) {
                throw new InvalidParameterException();
            }

            int i = 1;
            for(String s: attr) {
                pstmt.setString(i++, s);
            }

            ResultSet rs = pstmt.executeQuery();
//            pstmt.close();
//            this.conn.close();
            return rs;
        }

        public void updateSQL(String query, String[] queryAttr) throws SQLException {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            for(int i = 1; i <= queryAttr.length; ++i) {
                pstmt.setString(i, queryAttr[i - 1]);
            }

            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
            conn.close();
        }
}
