package Controller;

import Model.Login;
import Service.LoginService;
import Service.RegisterService;
import View.LoginForm;
import View.helpers.FormParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class LoginController implements HttpHandler {
    LoginService loginService = new LoginService();
    LoginForm loginForm = new LoginForm();
    RegisterService registerService = new RegisterService();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        String method = httpExchange.getRequestMethod();
        try {
            if(isSubmitted(httpExchange, method)) {
                Map<String, String> inputs = FormParser.parseFormData(httpExchange);
                if(inputs.size() == 2) {
                    Login login = loginForm.retrieveLogin(inputs);
                    loginService.login(httpExchange, login);
                } else if(inputs.size() == 4){
                    registerService.register(inputs);
                    loginService.loadPanel(httpExchange);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isSubmitted(HttpExchange httpExchange, String method) throws IOException, SQLException {
        if(method.equals("GET")){
            loginService.loadPanel(httpExchange);
            return false;
        }

        return true;
    }


}
