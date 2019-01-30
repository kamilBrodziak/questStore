package Service;

import Controller.helpers.CookieHandler;
import Controller.helpers.TwigLoader;
import DAO.*;
import Model.Session;
import com.sun.net.httpserver.HttpExchange;

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

    private void login(HttpExchange httpExchange, String login) throws IOException, SQLException{
        if(cookieHandler.isNewSession(httpExchange)) {
            sessionDAO.addSession(new Session(UUID.randomUUID().toString(), loginDAO.getLoginByLoginName(login).getId(),
                    new Timestamp(System.currentTimeMillis())));
            cookieHandler.createNewSession(httpExchange, Integer.toString(sessionDAO.getSessionCount()));
        }
        loadSession(httpExchange);
    }

    private void logout(HttpExchange httpExchange, String session) throws IOException, SQLException{
        sessionDAO.deleteSession(session);
        cookieHandler.deleteCookie(httpExchange);
    }

    public void loadSession(HttpExchange httpExchange) throws IOException, SQLException {
        if(cookieHandler.isNewSession(httpExchange)) {
            redirectToPage(httpExchange, "index.twig");
        } else {
            redirectToPanel(httpExchange);
        }
    }

    private void redirectToPage(HttpExchange httpExchange, String template) throws IOException {
        String response = twigLoader.loadTemplate(httpExchange, template, new HashMap<>());
        twigLoader.sendResponse(httpExchange, response);
    }

    private void redirectToPanel(HttpExchange httpExchange) throws IOException, SQLException {
        Optional<HttpCookie> cookie = cookieHandler.getSessionIdCookie(httpExchange);
        String session = cookie.get().getName();

        int loginID = sessionDAO.getSession(session).getLoginID();

        if(studentDAO.getStudentByLoginID(loginID) != null) {
            redirectToPage(httpExchange, "studentPanel.twig");
        } else if(mentorDAO.getMentorByLoginID(loginID) != null) {
            redirectToPage(httpExchange, "mentorPanel.twig");
        } else if (adminDAO.getAdminByLoginID(loginID) != null){
            redirectToPage(httpExchange, "adminPanel.twig");
        } else {
            redirectToPage(httpExchange, "index.twig");
        }
    }
}
