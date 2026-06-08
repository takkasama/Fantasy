package cr.ac.una.fantasydefender.util;

import java.util.Arrays;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * @author takka_sama 
 */
public class EmailManager {

    private static EmailManager instance;
    
    private Properties props;
    private Session    session;
    private String     emailUser;
    private boolean    initialized = false;
    
    private String host = "smtp.gmail.com";
    private String port = "587";

    private EmailManager() {}

    public static EmailManager getInstance() {
        if (instance == null) {
            synchronized (EmailManager.class) {
                if (instance == null) instance = new EmailManager();
            }
        }
        return instance;
    }

    public void setHost(String host) { this.host = host; }
    
    public void setPort(String port) { this.port = port; }

    /**
     * @param emailUser    dirección de correo del remitente
     * @param emailPassword contraseña como String  - Borra al finalizar el metodo
     */
    public void init(String emailUser, char[] emailPassword) {
        if (emailPassword == null || emailPassword.length == 0)
            throw new IllegalArgumentException("The password is necesary to open session.");

        definePropertiesConnection();
        this.emailUser = emailUser;
        final String pwd = new String(emailPassword);

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUser, pwd);
            }
        });

        session.setDebug(false); 
        Arrays.fill(emailPassword, '\0');
        initialized = true;
    }

    private void definePropertiesConnection() {
        props = new Properties();
        props.put("mail.smtp.host",             host);
        props.put("mail.smtp.port",             port);
        props.put("mail.smtp.auth",             "true");
        props.put("mail.smtp.starttls.enable",  "true");
        props.put("mail.smtp.connectiontimeout","10000");
        props.put("mail.smtp.timeout",          "10000");
    }

    /**
     * @param emailReceptor  dirección de destino
     * @param subject        asunto
     * @param htmlBody       cuerpo en HTML
     * @param plainBody      cuerpo alternativo en texto plano (para clientes sin HTML)
     * @return               Message listo para enviar
     */
    public Message buildMessage(String emailReceptor, String subject, String htmlBody, String plainBody)
            throws MessagingException {
        checkInitialized();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailUser));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceptor));
        message.setSubject(subject);
        message.setSentDate(new java.util.Date());

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(plainBody, "UTF-8");

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlBody, "text/html; charset=UTF-8");

        MimeMultipart alternative = new MimeMultipart("alternative");
        alternative.addBodyPart(textPart);
        alternative.addBodyPart(htmlPart);

        message.setContent(alternative);
        return message;
    }

    /**
     *
     * @return true si el envío fue exitoso, false en caso contrario
     */
    public boolean sendMessage(String emailReceptor, String subject, String htmlBody, String plainBody) {
        try {
            Transport.send(buildMessage(emailReceptor, subject, htmlBody, plainBody));
            return true;
        } catch (AuthenticationFailedException e) {
            System.err.println("[EmailManager] Autenticación fallida: " + e.getMessage());
        } catch (SendFailedException e) {
            System.err.println("[EmailManager] Dirección inválida o rechazada: " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println("[EmailManager] Error de red o protocolo: " + e.getMessage());
        }
        return false;
    }

    
    public boolean sendCodeToConfirmEmail(String emailReceptor, String code){
        
       String htmlBody = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
        </head>
        <body style="font-family: Arial, sans-serif; background:#f4f4f4; padding:20px;">
            <div style="max-width:500px; margin:auto; background:white; padding:30px; border-radius:10px; text-align:center;">
                <h2 style="color:#333;">Email Verification</h2>
                <p>Your verification code is:</p>
                <div style="
                    font-size:32px;
                    font-weight:bold;
                    letter-spacing:5px;
                    color:#2563eb;
                    margin:20px 0;">
                    %s
                </div>
            </div>
        </body>
        </html>
        """.formatted(code);

        String plainBody = "Your verification code is: " + code;
        
        return sendMessage(emailReceptor, "Confirm Code : User Register To Pruebas Graficas", htmlBody, plainBody);
    }
    
    private void checkInitialized() {
        if (!initialized)
            throw new IllegalStateException(
                "EmailManager no inicializado. Llamar a init(user, password) primero.");
    }

    public boolean isInitialized() { return initialized; }
}