package Service;

import Controller.helpers.Accounts;
import Controller.helpers.CookieHandler;
import Controller.helpers.TwigLoader;
import DAO.*;
import Model.Session;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MenuService {
    DataBaseConnector dbCon = new DataBaseConnector();
    SessionDAO sessionDAO = new SessionDAOPostgreSQL(dbCon);
    private AdminDAO adminDAO = new AdminDAOPostgreSQL(dbCon);
    private MentorDAO mentorDAO = new MentorDAOPostgreSQL(dbCon);
    private StudentDAO studentDAO = new StudentDAOPostgreSQL(dbCon);
    private CookieHandler cookieHandler = new CookieHandler();
    private TwigLoader twigLoader = new TwigLoader();

    public void loadPageWithMenu(HttpExchange httpExchange, String templateName,
                                 Map<String, String> map, Accounts accountType) throws IOException, SQLException {
        String session = "";
        Optional<HttpCookie> cookie = cookieHandler.getSessionIdCookie(httpExchange);
        if(cookie.isPresent()) {
            session = cookie.get().getValue();
        }


        Session sessionObj = sessionDAO.getSession(session.replaceAll("\\\"", ""));
        String[] keys = {"main", "account", "quests", "contact", "accountDis", "questsDis", "logoutDis"};

        map.putAll(putArguments(getValues(sessionObj), keys));
        if(canBeLoaded(sessionObj, accountType)) {
            String response = twigLoader.loadTemplate(httpExchange, templateName, map);
            twigLoader.sendResponse(httpExchange, response);
        } else {
            twigLoader.redirectToPage(httpExchange, "../index");
        }

    }

    private String[] getValues(Session sessionObj) throws SQLException{
        if(sessionObj != null) {

            int loginID = sessionObj.getLoginID();
            if(studentDAO.getStudentByLoginID(loginID) != null) {
                return new String[]{"../main", "../student/studentPanel", "../student/quests", "../contact"};
            } else if(mentorDAO.getMentorByLoginID(loginID) != null) {
                return new String[]{"main", "mentor/mentorMenu", "mentor/questManager", "contact"};

            } else if (adminDAO.getAdminByLoginID(loginID) != null){
                return new String[]{"main", "admin/dupa", "admin/dupa", "contact"};
            }
        }
        return new String[]{"index", "index", "index", "contact", "disabled", "disabled", "disabled"};
    }

    private Map<String, String> putArguments(String[] values, String[] keys) {
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < values.length; ++i) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    private boolean canBeLoaded(Session session, Accounts accountType) throws SQLException{
        if(accountType == Accounts.UNIVERSAL) {
            return true;
        } else if(session != null && (accountType == Accounts.STUDENT && studentDAO.getStudentByLoginID(session.getLoginID()) != null ||
                    accountType == Accounts.ADMIN && adminDAO.getAdminByLoginID(session.getLoginID()) != null ||
                    accountType == Accounts.MENTOR && mentorDAO.getMentorByLoginID(session.getLoginID()) != null)) {
            return true;
        } else if(accountType == Accounts.USER) {
            return true;
        }
        return false;
    }
}
