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
        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){
//            TwigLoader twigLoader = new TwigLoader();
//            String response = twigLoader.loadTemplate(httpExchange, "index.twig", new HashMap<>());
//            twigLoader.sendResponse(httpExchange, response);
            loginService.loadSession(httpExchange);
            return false;
        }

        return true;
    }


}
