package Controller;

import Controller.helpers.Accounts;
import Controller.helpers.CurrentUserHelper;
import DAO.DataBaseConnector;
import DAO.QuestDAO;
import Model.Artifact;
import Service.MenuService;
import Service.QuestService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestManager implements HttpHandler {
    private MenuService menuService = new MenuService();

    private DataBaseConnector dataBaseConnector = new DataBaseConnector();
    private CurrentUserHelper currentUserHelper = new CurrentUserHelper();
//    private QuestDAO questDAO = new QuestDAO(dataBaseConnector);
    private QuestService questService = new QuestService(dataBaseConnector);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Map<String, Object> map = new HashMap<>();

//        Integer id = currentUserHelper.getCurrentStudentID(httpExchange);
        try {
//            List<Artifact> list = questService.getStudentQuestList(id);
//            map.put("listOfQuests", list);
            menuService.loadPageWithMenu(httpExchange, "questManager", map, Accounts.STUDENT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}