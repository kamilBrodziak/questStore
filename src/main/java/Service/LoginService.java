package Service;

import Controller.helpers.CookieHandler;
import Controller.helpers.TwigLoader;
import DAO.*;
import Model.Login;
import Model.Session;
import com.sun.net.httpserver.HttpExchange;

import javax.xml.ws.http.HTTPBinding;
import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;


public class LoginService {
    private DataBaseConnector dbCon = new DataBaseConnector();
    private SessionDAO sessionDAO = new SessionDAOPostgreSQL(dbCon);
    private AdminDAO adminDAO = new AdminDAOPostgreSQL(dbCon);
    private MentorDAO mentorDAO = new MentorDAOPostgreSQL(dbCon);
    private StudentDAO studentDAO = new StudentDAOPostgreSQL(dbCon);
    private LoginDAO loginDAO = new LoginDAOPostgreSQL(dbCon);
    private CookieHandler cookieHandler = new CookieHandler();
    private TwigLoader twigLoader = new TwigLoader();

    public boolean login(HttpExchange httpExchange, Login login) throws IOException, SQLException{
        if(login != null && cookieHandler.isNewSession(httpExchange)) {
            Login correctLogin = loginDAO.getLoginByLoginName(login.getLogin());
            if(correctLogin!= null && correctLogin.getPassword().equals(login.getPassword())) {
                Session session = new Session(UUID.randomUUID().toString(),
                        loginDAO.getLoginByLoginName(login.getLogin()).getId(),
                        new Timestamp(System.currentTimeMillis()));
                sessionDAO.addSession(session);
                cookieHandler.createNewSession(httpExchange, session.getSession());
                redirectToPanel(httpExchange, session.getSession());
                return true;
            }
        }
        loadPage(httpExchange, "index");
        return false;
    }

    public void logout(HttpExchange httpExchange, String session) throws IOException, SQLException{
        sessionDAO.deleteSession(session);
        cookieHandler.deleteCookie(httpExchange);
        redirectToPage(httpExchange, "index");
    }

    public void loadSession(HttpExchange httpExchange) throws IOException, SQLException {
        if(cookieHandler.isNewSession(httpExchange)) {
            loadPage(httpExchange, "index");
        } else {
            Optional<HttpCookie> cookie = cookieHandler.getSessionIdCookie(httpExchange);
            String session = cookie.get().getValue().replaceAll("\\\"", "");
            redirectToPanel(httpExchange, session);
        }
    }

    private void redirectToPage(HttpExchange httpExchange, String page) throws IOException {
        twigLoader.redirectToPage(httpExchange, page);
    }

    private void loadPage(HttpExchange httpExchange, String template) throws IOException{
        String response = twigLoader.loadTemplate(httpExchange, template, new HashMap<>());
        twigLoader.sendResponse(httpExchange, response);
    }

    private void redirectToPanel(HttpExchange httpExchange, String session) throws IOException, SQLException {
        int loginID = sessionDAO.getSession(session).getLoginID();
        if(studentDAO.getStudentByLoginID(loginID) != null) {
            redirectToPage(httpExchange, "student/studentPanel");
        } else if(mentorDAO.getMentorByLoginID(loginID) != null) {
            redirectToPage(httpExchange, "mentorPanel");
        } else if (adminDAO.getAdminByLoginID(loginID) != null){
            redirectToPage(httpExchange, "adminPanel");
        } else {
            redirectToPage(httpExchange, "index");
        }
    }

}
