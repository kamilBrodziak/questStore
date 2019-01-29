package Controller;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;


public class Server {
    public static void main(String[] args) throws Exception {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        // set routes
        server.createContext("/public_html", new Static());
        server.createContext("/template", new Template());

        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}