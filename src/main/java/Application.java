import Controller.*;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;


public class Application {
    public static void main(String[] args) throws Exception {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        // set routes
        server.createContext("/static", new Static());
        server.createContext("/student/studentPanel", new StudentController());
        server.createContext("/index", new LoginController());
        server.createContext("/logout", new LogoutController());
        server.createContext("/contact", new ContactController());
        server.createContext("/main", new MainController());
        server.createContext("/student/quests", new QuestController());

        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}