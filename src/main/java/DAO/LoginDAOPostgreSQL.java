package DAO;

import Model.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOPostgreSQL implements LoginDAO{
    private DataBaseConnector dataBaseConnector;

    public LoginDAOPostgreSQL(DataBaseConnector dbCon){
        this.dataBaseConnector = dbCon;
    }

    public void addLogin(Login login) throws SQLException {
        String query = "INSERT INTO logins (login, password) VALUES (?, ?);";
        String[] queryAttr = {login.getLogin(), login.getPassword()};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void editLogin(Login login) throws SQLException{
        String query = "UPDATE logins SET login = ?, password = ? WHERE id = ?;";
        String[] queryAttr = {login.getLogin(), login.getPassword(), Integer.toString(login.getId())};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void updatePassword(Login login) throws SQLException {
        String query = "UPDATE logins SET password = ? WHERE id = ?;";
        String[] queryAttr = {login.getPassword(), Integer.toString(login.getId())};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void deleteLogin(int id) throws SQLException{
        String query = "DELETE FROM logins WHERE id = ?";
        String[] queryAttr = {Integer.toString(id)};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public List<Login> getLogins() throws SQLException{
        String query = "SELECT name, surname, email, city, begin_work FROM mentors;";
        List<Login> loginList = new ArrayList<>();

        ResultSet rs = dataBaseConnector.query(query, null);

        while(rs.next()){
            Login login = new Login(rs.getInt("id"), rs.getString("login"), rs.getString("password"));

            loginList.add(login);
        }
        return loginList;
    }

    public Login getLogin(int id) throws SQLException{
        String query = "SELECT * FROM logins WHERE id = ?;";
        String[] queryAttr = {Integer.toString(id)};

        ResultSet rs = dataBaseConnector.query(query, queryAttr);

        if(rs.next()){
            return new Login(rs.getInt("id"), rs.getString("login"), rs.getString("password"));
        }
        return null;
    }

    public Login getLoginByLoginName(String login) throws SQLException{
        String query = "SELECT * FROM logins WHERE login = ?;";
        String[] queryAttr = {login};

        ResultSet rs = dataBaseConnector.query(query, queryAttr);

        if(rs.next()){
            return new Login(rs.getInt("id"), rs.getString("login"), rs.getString("password"));

        }
        return null;
    }

    public boolean checkIfPasswordIsCorrect(String passwordFromInput) throws SQLException {
        String query = "SELECT password FROM logins WHERE id=?";
        String[] queryAttr = {passwordFromInput};
        ResultSet rs = dataBaseConnector.query(query, queryAttr);

        while(rs.next()){
            String passwordFromDb = rs.getString("password");

            if(passwordFromDb.equals(passwordFromInput)){
                return true;
            }
        }
        return false;
    }
}
