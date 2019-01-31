package Controller;

import Controller.helpers.CookieHandler;
import Service.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class LogoutController implements HttpHandler {
    LoginService loginService = new LoginService();
    CookieHandler cookieHandler = new CookieHandler();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            loginService.logout(httpExchange,
                    cookieHandler.getSessionIdCookie(httpExchange).get().getValue().replaceAll("\\\"", ""));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
