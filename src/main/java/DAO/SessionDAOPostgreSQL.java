package DAO;

import Model.Level;
import Model.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAOPostgreSQL implements SessionDAO {
    private DataBaseConnector dataBaseConnector;

    public SessionDAOPostgreSQL(DataBaseConnector dataBaseConnector){
        this.dataBaseConnector = dataBaseConnector;
    }

    public void addSession(Session session) throws SQLException {
        String query = "INSERT INTO loginSessions (session, id_logins, end_date) VALUES (?);";
        String[] queryAttr = {session.getSession(),
                Integer.toString(session.getLoginID()), session.getDate().toString()};
        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void deleteSessionByLoginID(int loginID) throws SQLException{
        String query = "DELETE loginSessions WHERE id_logins=?;";
        String[] queryAttr = {Integer.toString(loginID)};
        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void deleteSession(String session) throws SQLException {
        String query = "DELETE loginSessions WHERE session=?;";
        String[] queryAttr = {"session"};
        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public Session getSession(String session) throws SQLException {
        String query = "SELECT * FROM loginSessions WHERE session=?";

        String[] queryAttr = {session};
        ResultSet rs = dataBaseConnector.query(query, queryAttr);
        if(rs.next()) {
            return new Session(rs.getString("session"), rs.getInt("id_logins"), rs.getTimestamp("end_time"));
        }
        return null;
    }

    public Session getSessionByLoginID(int loginID) throws SQLException {
        String query = "SELECT * FROM loginSessions WHERE id_logins=?";

        String[] queryAttr = {Integer.toString(loginID)};
        ResultSet rs = dataBaseConnector.query(query, queryAttr);
        if(rs.next()) {
            return new Session(rs.getString("session"), rs.getInt("id_logins"), rs.getTimestamp("end_time"));
        }
        return null;
    }

    public int getSessionCount() throws SQLException {
        String query = "SELECT COUNT(*) count FROM loginSessions WHERE id_logins=?";
        String[] queryAttr = {};
        ResultSet rs = dataBaseConnector.query(query, queryAttr);
        if(rs.next()) {
            rs.getInt("count");
        }
        return 0;
    }
}
