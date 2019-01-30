package DAO;

import Model.Session;

import java.sql.SQLException;

public interface SessionDAO {
    public void deleteSession(String session) throws SQLException;
    public void deleteSessionByLoginID(int loginID) throws SQLException;
    public void addSession(Session session) throws SQLException;
    public Session getSession(String session) throws SQLException;
    public int getSessionCount() throws SQLException;
}
