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

        //get a filename from request
        String uri = httpExchange.getRequestURI().getPath();
        String templateName = uri.substring(uri.lastIndexOf("/")+1);

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/" + templateName + ".twig");

        // render a template to a string
        String response = template.render(createModelForTemplate(templateName));

        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private JtwigModel createModelForTemplate(String templateName) {
        switch (templateName) {
            case "studentPanel" : return createModelForStudentPanel();
        }
        return null;
    }

    private JtwigModel createModelForStudentPanel() {
        JtwigModel model = JtwigModel.newModel();
        model.with("coins", 500);
        model.with("level", "FABULOUS");

        return model;
    }

}
