import Controller.Static;
import Controller.StudentController;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;


public class Application {
    public static void main(String[] args) throws Exception {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        // set routes
        server.createContext("/static", new Static());
        server.createContext("/student", new StudentController());
//        server.createContext("/static/studentPanel.html?", new StudentController());

        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}