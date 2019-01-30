package Controller.helpers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.List;
import java.util.Optional;

public class CookieHandler implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    CookieHelper cookieHelper = new CookieHelper();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Optional<HttpCookie> cookie = getSessionIdCookie(httpExchange);

        boolean isNewSession;
        if (cookie.isPresent()) {  // Cookie already exists
            isNewSession = false;
        } else { // Create a new cookie
            isNewSession = true;
            String sessionId = ""; // This isn't a good way to create sessionId. Find a better one!
            cookie = Optional.of(new HttpCookie(SESSION_COOKIE_NAME, sessionId));
            httpExchange.getResponseHeaders().add("Set-Cookie", cookie.get().toString());
        }


//        sendResponse(httpExchange, response);
    }

    public boolean isNewSession(HttpExchange httpExchange) {
        return !getSessionIdCookie(httpExchange).isPresent();
    }

    public void createNewSession(HttpExchange httpExchange, String session) {
        boolean isNewSession = true;
        String sessionId = session; // This isn't a good way to create sessionId. Find a better one!
        Optional<HttpCookie> cookie = Optional.of(new HttpCookie(SESSION_COOKIE_NAME, sessionId));
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.get().toString());
    }

    public Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }

    public void deleteCookie(HttpExchange httpExchange) {
        Optional<HttpCookie> sessionCookieOptional = getSessionIdCookie(httpExchange);
        HttpCookie sessionCookie = sessionCookieOptional.get();
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/");
        sessionCookie.setValue("");
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}