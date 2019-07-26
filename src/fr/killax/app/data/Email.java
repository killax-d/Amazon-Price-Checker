package fr.killax.app.data;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import fr.killax.app.resources.HTMLPage;
import fr.killax.app.utils.StringHTMLBuilder;

public class Email {
	
	private User user;
	private Product product;
	
	public Email(User user, Product product) {
		this.user = user;
		this.product = product;
	}

	
	public void send() throws Exception{
		String host = "smtp.domain.fr"; 						// define mail server host name
		String from = "amazonchecker@domain.fr"; 				// define sending email address
		String to = user.getEmail(); 							// define receiving email address
		String username = "username@domain.fr"; 				// define sending email username
		String password = "password";							// define sending email password
		
		Properties props = new Properties(); 					// Get system properties 
		props.put("mail.smtp.host", host);						// Setup mail server host name
		props.put("mail.smtp.port", "587");						// Setup mail server port
		props.put("mail.smtp.auth", "true"); 					// Setup mail server authentication to true
		props.put("mail.smtp.starttls.enable","true");			// Setup mail server SSL to true

		Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
		
		String content = StringHTMLBuilder.format(HTMLPage.getPage("templates/mail.html"), 
				user, 
				product.getName(), 
				product.getPrice(), 
				product.getImageUrl(),
				product.getName(), 
				product.getFeatures(), 
				product.getDelivry()
			);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
        );
		message.setSubject("Amazon Price Checker Alert"); 		// Define message subject
		message.setContent(content,
				"text/html; charset=utf-8"); 					// Define message content

		System.out.println(String.format("Trying to send email to %s...", to));
        Transport.send(message);

		System.out.println(String.format("Email was sent to : %s about : %s at %s", to, product.getName(), product.getPrice()));
	}
}
