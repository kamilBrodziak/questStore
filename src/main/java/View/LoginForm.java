package View;

import View.helpers.FormParser;
import Model.Login;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class LoginForm {

    public Login retrieveLogin(Map<String, String> inputs) throws IOException {
        return new Login(0, inputs.get("login"), inputs.get("password"));
    }
}
