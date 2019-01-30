package Service;

import Controller.helpers.CookieHandler;
import Controller.helpers.TwigLoader;
import DAO.*;
import Model.Session;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;


public class LoginService {
    private DataBaseConnector dbCon = new DataBaseConnector();
    private SessionDAOPostgreSQL sessionDAOPostgreSQL = new SessionDAOPostgreSQL(dbCon);
    private AdminDAO adminDAO = new AdminDAOPostgreSQL(dbCon);
    private MentorDAO mentorDAO = new MentorDAOPostgreSQL(dbCon);
    private StudentDAO studentDAO = new StudentDAOPostgreSQL(dbCon);
    private CookieHandler cookieHandler = new CookieHandler();
    private TwigLoader twigLoader = new TwigLoader();

    private String login(HttpExchange httpExchange, String login) {

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
        String sessionId = cookie.get().getName();

        int loginID = sessionDAOPostgreSQL.getSessionByID(Integer.parseInt(sessionId)).getLoginID();

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
