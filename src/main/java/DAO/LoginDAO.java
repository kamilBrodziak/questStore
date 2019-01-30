package DAO;

import Model.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LoginDAO {
    public void addLogin(Login login) throws SQLException;

    public void editLogin(Login login) throws SQLException;

    public void deleteLogin(int id) throws SQLException;

    public List<Login> getLogins() throws SQLException;

    public Login getLogin(int id) throws SQLException;

    public Login getLoginByLoginName(String login) throws SQLException;
}
