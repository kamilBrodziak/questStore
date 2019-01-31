package Controller.helpers;


import DAO.*;
import Model.Login;
import Model.Session;
import Model.Student;
import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CurrentUserHelper {

    private CookieHelper cookieHelper = new CookieHelper();
    private DataBaseConnector dataBaseConnector = new DataBaseConnector();
    private SessionDAO sessionDAOPostgreSQL = new SessionDAOPostgreSQL(dataBaseConnector);
    private StudentDAO studentDAOPostgreSQL = new StudentDAOPostgreSQL(dataBaseConnector);
    private LoginDAO loginDAOPostgreSQL = new LoginDAOPostgreSQL(dataBaseConnector);

    public Integer getCurrentStudentID(HttpExchange httpExchange){

        Optional<HttpCookie> cookie = getSessionIdCookie(httpExchange);
        try{
            Session session = sessionDAOPostgreSQL.getSession(cookie.get().getValue().replaceAll("\\\"", ""));
            Login login = loginDAOPostgreSQL.getLogin(session.getLoginID());
            Student student = studentDAOPostgreSQL.getStudentByLoginID(session.getLoginID());
            return student.getId();
        }catch(SQLException e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    private Optional<HttpCookie> getSessionIdCookie(com.sun.net.httpserver.HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName("sessionId", cookies);
    }
}
