package Controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class StudentController implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/panel.twig");

        // render a template to a string
        String response = template.render(getPanelLogin());

        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private JtwigModel getPanelLogin() {
        JtwigModel model = JtwigModel.newModel();
        model.with("coins", "Peter");
        model.with("level", "Pater");

        return model;
    }

}
