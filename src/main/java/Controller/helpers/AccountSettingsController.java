package Controller.helpers;

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
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

public class AccountSettingsController implements HttpHandler {

    SessionDAOPostgreSQL sessionDAOPostgreSQL;
    DataBaseConnector dataBaseConnector;
    CookieHelper cookieHelper;
    StudentDAOPostgreSQL studentDAOPostgreSQL;
    LoginDAOPostgreSQL loginDAOPostgreSQL;

    public AccountSettingsController(){
        this.dataBaseConnector = new DataBaseConnector();
        this.sessionDAOPostgreSQL = new SessionDAOPostgreSQL(dataBaseConnector);
        this.cookieHelper = new CookieHelper();
        this.studentDAOPostgreSQL = new StudentDAOPostgreSQL(dataBaseConnector);
        this.loginDAOPostgreSQL = new LoginDAOPostgreSQL(dataBaseConnector);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        Optional<HttpCookie> cookie = getSessionIdCookie(httpExchange);

        Student student = null;
        Login login = null;

        if(method.equals("GET")){
            try{
                Session session = sessionDAOPostgreSQL.getSession(cookie.get().getValue());
                login = loginDAOPostgreSQL.getLogin(session.getLoginID());
                student = studentDAOPostgreSQL.getStudent(session.getLoginID());
            }catch(SQLException e){
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }

            List<String> nameList = Arrays.asList("email", "password");
            List<String> gettersList = Arrays.asList(student.getEmail(), login.getPassword());

            response = renderTemplate(nameList, gettersList, "accountSettings.twig");
        }

        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println(formData);
            Map inputs = parseFormData(formData);

            String email = String.valueOf(inputs.get("email"));
            String oldPassword = String.valueOf(inputs.get("oldPassword"));
            String newPassword = String.valueOf(inputs.get("newPassword"));
            String confirmPassword = String.valueOf(inputs.get("confirmPassword"));

            try {
                if(isPasswordCorrectAndEmailNotExistsInDb(email, oldPassword, newPassword, confirmPassword)){
                    Session session = sessionDAOPostgreSQL.getSession(cookie.get().getValue());
                    login = loginDAOPostgreSQL.getLogin(session.getLoginID());
                    student = studentDAOPostgreSQL.getStudent(session.getLoginID());

                    loginDAOPostgreSQL.updatePassword(login);
                    studentDAOPostgreSQL.updateEmail(student);

                    response = renderTemplate(null, null, "accountSettings-change.twig");
                }else{
                    response = renderTemplate(null, null, "accountSettings-incorrect.twig");
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName("sessionId", cookies);
    }

    private String renderTemplate(List<String> nameList, List<String> gettersList, String templateName){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/" + templateName);
        JtwigModel model = JtwigModel.newModel();

        for(int i=0; i<nameList.size(); i++){
            model.with(nameList.get(i), gettersList.get(i));
        }
        return template.render(model);
    }

    private boolean isPasswordCorrectAndEmailNotExistsInDb(String email, String oldPassword, String newPassword, String confirmPassword) throws SQLException {
        return studentDAOPostgreSQL.checkIfEmailExists(email) && loginDAOPostgreSQL.checkIfPasswordIsCorrect(oldPassword)
                && newPassword.equals(confirmPassword);
    }
}
