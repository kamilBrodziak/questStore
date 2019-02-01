package Controller;

import Controller.helpers.Accounts;
import Controller.helpers.TwigLoader;
import Service.MenuService;
import View.helpers.FormParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ContactController implements HttpHandler {
    private MenuService menuService = new MenuService();
    private Properties emailProperties;
    private Session mailSession;
    private MimeMessage emailMessage;
    private TwigLoader twigLoader = new TwigLoader();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        String method = httpExchange.getRequestMethod();
        String response="";
        try {
            if(isSubmitted(httpExchange, method)) {
                Map inputs = FormParser.parseFormData(httpExchange);

                String emailSubject = "Name: " + inputs.get("name") + ", from email: " + inputs.get("email");
                String emailBody = (String) inputs.get("message");

                try {
                    setMailServerProperties();
                    createEmailMessage(emailSubject, emailBody);
                    sendEmail();
                }catch(MessagingException e){
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
                Map<String, Object> loadMap = new HashMap<>();
                menuService.loadPageWithMenu(httpExchange, "contact", loadMap, Accounts.UNIVERSAL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isSubmitted(HttpExchange httpExchange, String method) throws IOException, SQLException {
        if(method.equals("GET")){
            Map<String, Object> map = new HashMap<>();
            menuService.loadPageWithMenu(httpExchange, "contact", map, Accounts.UNIVERSAL);
            return false;
        }

        return true;
    }

    public void setMailServerProperties() {

        String emailPort = "587";//gmail's smtp port

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

    }

    public void createEmailMessage(String emailSubject, String emailBody) throws AddressException,
            MessagingException {
        String[] toEmails = { "queststore.codecool@gmail.com", "user.codecool@gmail.com" };

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");//for a html email

    }

    public void sendEmail() throws AddressException, MessagingException {

        String emailHost = "smtp.gmail.com";
        String fromUser = "queststore.codecool@gmail.com";//just the id alone without @gmail.com
        String fromUserEmailPassword = "Queststore123!";

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }

}
