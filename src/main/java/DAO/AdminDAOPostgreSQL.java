package DAO;

import Model.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOPostgreSQL implements AdminDAO{
    private DataBaseConnector dataBaseConnector;

    public AdminDAOPostgreSQL(DataBaseConnector dataBaseConnector) {
        this.dataBaseConnector = dataBaseConnector;
    }

    @Override
    public Admin getAdminByLoginID(int loginID) throws SQLException {
        String query = "SELECT id, id_logins, name, surname, email FROM admins WHERE id_logins = ?;";
        String[] queryAttr = {Integer.toString(loginID)};

        ResultSet rs = dataBaseConnector.query(query, queryAttr);

        if(rs.next()) {
            return new Admin(rs.getInt("id"), rs.getInt("id_logins"),
                    rs.getString("name"), rs.getString("surname"),
                    rs.getString("email"));
        }
        return null;
    }
}
