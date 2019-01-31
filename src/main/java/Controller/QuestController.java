package Controller;

import Controller.helpers.Accounts;
import Service.MenuService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class QuestController implements HttpHandler {
    private MenuService menuService = new MenuService();
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Map<String, String> map = new HashMap<>();

        try {
            menuService.loadPageWithMenu(httpExchange, "studentQuests", map, Accounts.UNIVERSAL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
