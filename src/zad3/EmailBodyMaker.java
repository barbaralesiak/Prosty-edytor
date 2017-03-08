package zad3;
import java.awt.Color;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class EmailBodyMaker {
	String body;
	String backColString = "orange";
	String fontNameString = "sanserif";
	String fontColorString ="red";
	String fontSizeString ="24";
	String fontStyleString = "ROMAN_BASELINE";
	String from ;
	String to;
	
	
	/**
	 * Constructs HTML text from JTextArea content .
	 * @param a is JTextArea content.
	 */
	public EmailBodyMaker(JTextArea a){
		
		String s = a.getText();
		if(s.contains("#ustawienia")){
			int index = s.indexOf("#ustawienia");
			s = s.substring(0, index);
		}
		String content = "";
		StringBuffer buff = new StringBuffer();
		Scanner sc = new Scanner(s);
		while(sc.hasNextLine()){
			buff.append(sc.nextLine()).append("<br>");
		}
		content = buff.toString();
		backColString = transformColor(a.getBackground().toString());
		fontColorString = transformColor(a.getForeground().toString());
		fontNameString = a.getFont().getName().toString();
		fontSizeString = a.getFont().getSize() + "";
		fontStyleString = a.getFont().getStyle() +"";
		
		body = "<html><head></head><body style = background-color:"+backColString+ " style= line-height:150%> "
				+ "<font size =" + fontSizeString +    " color= " + fontColorString + " name= "
				+ fontNameString + " style=" +fontStyleString + ">" 
				 + content + "</font></body></html>";
		sc.close();
	}
	
	
	public String transformColor(String s){
		String color ="rgb(0,0,0)";
		if(s.contains("[")){
			color = "rgb(";
			int i = s.indexOf('[');
			s = s.substring(i);
		
		String [] tab = s.split(",");
		color += tab[0].substring(3) + ",";
		color += tab[1].substring(2) + ",";
		color += tab[2].substring(2, tab[2].length()-1) + ")";
		}
		return color;
	}
	public String getBody(){
		return this.body;
	}
	
	/**
	 * This method sends HTML email messages
	 * @param host is sender mailbox host.
	 * @param port is sender mailbox port number to send message.
	 * @param userName is sender mailbox address.
	 * @param password is sender mailbox password.
	 * @param toAddress is recipient address.
	 * @param subject is subject of message to be sent.
	 * @param message is HTML text of email to be sent.
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public  void sendHtmlEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {
		
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
     
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        
        Session session = Session.getInstance(properties, auth);
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setContent(message, "text/html; charset=ISO-8859-2");
        Transport.send(msg);
	}

}
