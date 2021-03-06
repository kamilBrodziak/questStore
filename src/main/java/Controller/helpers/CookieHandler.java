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
        if (cookie.isPresent() || cookie.get().getValue().replaceAll("\\\"", "").equals("null")) {  // Cookie already exists
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
        Optional<HttpCookie> cookie = getSessionIdCookie(httpExchange);
        return !cookie.isPresent() ||
                cookie.get().getValue().replaceAll("\\\"", "").equals("null");
    }

    public void createNewSession(HttpExchange httpExchange, String session){
        boolean isNewSession = true;
        String sessionId = session;
        Optional<HttpCookie> cookie = Optional.of(new HttpCookie(SESSION_COOKIE_NAME, sessionId));
        cookie.get().setMaxAge(-1);
        System.out.println(cookie.get().toString());
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.get().toString());
    }

    public Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }

    public void deleteCookie(HttpExchange httpExchange) throws IOException{
        Optional<HttpCookie> sessionCookieOptional = getSessionIdCookie(httpExchange);
        HttpCookie sessionCookie = sessionCookieOptional.get();
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/");
        sessionCookie.setValue(null);
        httpExchange.getResponseHeaders().add("Set-Cookie", sessionCookie.toString());
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}