package Controller;

import Controller.helpers.TwigLoader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class StudentController implements HttpHandler {
    private TwigLoader twigLoader = new TwigLoader();
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Map<String, String> map = new HashMap<>();
        map.put("coins", "Peter");
        map.put("level", "Pater");
        // send the results to a the client
        String response = twigLoader.loadTemplate(httpExchange, "studentPanel.twig", map);
        twigLoader.sendResponse(httpExchange, response);

    }

    private JtwigModel getPanelLogin() {
        JtwigModel model = JtwigModel.newModel();
        model.with("coins", "Peter");
        model.with("level", "Pater");

        return model;
    }

}
