package Controller;

import Controller.helpers.CookieHelper;
import View.helpers.FormParser;
import Controller.helpers.TwigLoader;
import DAO.DataBaseConnector;
import DAO.LoginDAOPostgreSQL;
import DAO.SessionDAOPostgreSQL;
import DAO.StudentDAOPostgreSQL;
import Model.Login;
import Model.Session;
import Model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.*;

public class AccountSettingsController implements HttpHandler {

    SessionDAOPostgreSQL sessionDAOPostgreSQL;
    DataBaseConnector dataBaseConnector;
    CookieHelper cookieHelper;
    StudentDAOPostgreSQL studentDAOPostgreSQL;
    LoginDAOPostgreSQL loginDAOPostgreSQL;
    TwigLoader twigLoader;

    public AccountSettingsController(){
        this.dataBaseConnector = new DataBaseConnector();
        this.sessionDAOPostgreSQL = new SessionDAOPostgreSQL(dataBaseConnector);
        this.cookieHelper = new CookieHelper();
        this.studentDAOPostgreSQL = new StudentDAOPostgreSQL(dataBaseConnector);
        this.loginDAOPostgreSQL = new LoginDAOPostgreSQL(dataBaseConnector);
        this.twigLoader = new TwigLoader();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> cookie = getSessionIdCookie(httpExchange);

        Student student = null;
        Login login = null;
        Session session;

        try {
            session = sessionDAOPostgreSQL.getSession(cookie.get().getValue().replaceAll("\\\"", ""));
            login = loginDAOPostgreSQL.getLogin(session.getLoginID());
            student = studentDAOPostgreSQL.getStudentByLoginID(session.getLoginID());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(method.equals("GET")){
            Map<String, Object> map = new HashMap<>();
            map.put("email", student.getEmail());
            map.put("password", login.getPassword());

            response = twigLoader.loadTemplate(httpExchange, "accountSettings", map);
        } else if (method.equals("POST")){
            Map inputs = FormParser.parseFormData(httpExchange);

            String email = String.valueOf(inputs.get("email"));
            String oldPassword = String.valueOf(inputs.get("oldPassword"));
            String newPassword = String.valueOf(inputs.get("newPassword"));
            String confirmPassword = String.valueOf(inputs.get("confirmPassword"));

            try {
                if(isPasswordCorrectAndEmailNotExistsInDb(email, oldPassword, newPassword, confirmPassword)){
                    login.setPassword(newPassword);
                    loginDAOPostgreSQL.editLogin(login);
                    student.setEmail(email);
                    studentDAOPostgreSQL.updateStudent(student);

                    response = twigLoader.loadTemplate(httpExchange, "accountSettingsChange", new HashMap<>());
                }else{
                    response = twigLoader.loadTemplate(httpExchange, "accountSettingsIncorrect", new HashMap<>());
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }

        twigLoader.sendResponse(httpExchange, response);
    }

    private Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName("sessionId", cookies);
    }

    private boolean isPasswordCorrectAndEmailNotExistsInDb(String email, String oldPassword, String newPassword, String confirmPassword) throws SQLException {
        return studentDAOPostgreSQL.checkIfEmailExists(email) &&
                loginDAOPostgreSQL.checkIfLoginExists(email) &&
                loginDAOPostgreSQL.checkIfPasswordIsCorrect(email, oldPassword) &&
                newPassword.equals(confirmPassword);
    }
}
