package Controller;

import Controller.helpers.Accounts;
import Service.MenuService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MainController implements HttpHandler {
    private MenuService menuService = new MenuService();
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String[] newsList = {"Rugor amidala zuggs spar arkanis iego til c-3po. Askajian mon dexter nass luke valorum chommell firmus corellia. Raa haako talz dexter mustafar windu neimoidia. Axmis gallia jabiimas skywalker tharin h'nemthean emtrey.",
                             "Barabel nar dat onimi ssi-ruuk zekk. Axmis gallia jabiimas skywalker tharin h'nemthean emtrey.",
                             "Iridonian jabba terentatek sykes. Ka aparo lepi raioballo tatooine atrivis leia trioculus. Mon c'baoth isard isolder finis tib vau max kal. Besalisk kir garm hapes x'ting paploo. Askajian mon dexter nass luke valorum chommell firmus corellia. "};
        String[] lastAchieved = {"Tano lars ki-adi-mundi",
                                 "Skywalker noa caedus baba joruus",
                                 "Sifo-dyas desann skywalker",
                                 "Seerdon thrawn r2-d2",
                                 "Axmis gallia jabiimas",
                                 "Rishi metalorn saffa",
                                 "Iridonian jabba"};
        String[] topFive = {"Kamino Skywalker",
                            "Darth Devaronian",
                            "Aayla Jade",
                            "Windu Nelvaanian",
                            "Darth Aqualish."};

        Map<String, Object> map = new HashMap<>();
        map.put("news", newsList);
        map.put("last_achieved", lastAchieved);
        map.put("top_five", topFive);

        try {
            menuService.loadPageWithMenu(httpExchange, "main", map, Accounts.USER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
