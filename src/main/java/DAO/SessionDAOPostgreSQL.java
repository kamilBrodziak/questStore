package DAO;

import Model.Level;
import Model.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAOPostgreSQL {
    private DataBaseConnector dataBaseConnector;

    public SessionDAOPostgreSQL(DataBaseConnector dataBaseConnector){
        this.dataBaseConnector = dataBaseConnector;
    }

    public void addSession(int loginID) throws SQLException {
        String query = "INSERT INTO loginSessions (id_logins) VALUES (?);";
        String[] queryAttr = {Integer.toString(loginID)};
        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void deleteSession(int loginID) throws SQLException{
        String query = "DELETE loginSessions WHERE id_logins=?;";
        String[] queryAttr = {Integer.toString(loginID)};
        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public Session getSessionByID(int loginID) throws SQLException {
        String query = "SELECT * FROM loginSessions WHERE id=?";

        String[] queryAttr = {Integer.toString(loginID)};
        ResultSet rs = dataBaseConnector.query(query, queryAttr);
        if(rs.next()) {
            return new Session(rs.getInt("id"), rs.getInt("id_logins"));
        }
        return null;
    }

    public Session getSessionByLoginID(int loginID) throws SQLException {
        String query = "SELECT * FROM loginSessions WHERE id_logins=?";

        String[] queryAttr = {Integer.toString(loginID)};
        ResultSet rs = dataBaseConnector.query(query, queryAttr);
        if(rs.next()) {
            return new Session(rs.getInt("id"), rs.getInt("id_logins"));
        }
        return null;
    }
}
