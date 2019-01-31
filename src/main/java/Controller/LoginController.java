package Controller;

import Model.Login;
import Service.LoginService;
import View.LoginForm;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController implements HttpHandler {
    LoginService loginService = new LoginService();
    LoginForm loginForm = new LoginForm();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        String method = httpExchange.getRequestMethod();
        try {
            if(isSubmitted(httpExchange, method)) {
                Login login = loginForm.retrieveLogin(httpExchange);
                loginService.login(httpExchange, login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isSubmitted(HttpExchange httpExchange, String method) throws IOException, SQLException {
        if(method.equals("GET")){
            loginService.loadSession(httpExchange);
            return false;
        }

        return true;
    }


}
