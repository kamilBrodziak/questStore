package Service;

import DAO.*;
import Model.Login;
import Model.Student;

import java.sql.SQLException;
import java.util.Map;

public class RegisterService {
    private DataBaseConnector dbCon = new DataBaseConnector();
    private StudentDAO studentDAO = new StudentDAOPostgreSQL(dbCon);
    private LoginDAO loginDAO = new LoginDAOPostgreSQL(dbCon);

    public void register(Map<String, String> inputs) throws SQLException {
        if(loginDAO.getLoginByLoginName(inputs.get("email")) == null) {

            loginDAO.addLogin(new Login(0, inputs.get("email"), inputs.get("password")));

            studentDAO.addStudent(new Student(0, loginDAO.getLoginByLoginName(inputs.get("email")).getId(),
                    inputs.get("firstName"), inputs.get("lastName"), inputs.get("email")));

        }
    }
}
