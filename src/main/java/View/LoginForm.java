package View;

import View.helpers.FormParser;
import Model.Login;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class LoginForm {

    public Login retrieveLogin(HttpExchange httpExchange) throws IOException {
        try {
            Map<String, String> inputs = FormParser.parseFormData(httpExchange);
            return new Login(0, inputs.get("login"), inputs.get("password"));

        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
