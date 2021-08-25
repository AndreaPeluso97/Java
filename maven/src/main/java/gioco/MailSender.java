package gioco;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;



public class MailSender extends Thread{
	private String usr,pwd,to,subject,body;
	
	public MailSender(String usr, String pwd, String to, String subject, String body){
		this.usr=usr;
		this.pwd=pwd;
		this.to=to;
		this.subject=subject;
		this.body=body;
		start();
	}
	
	public void run(){
		try {
			send_email(usr, pwd, to, subject, body);
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null, "Errore invio email all'utente: " +to+ " , messaggio: "+body);
		}
	}

	public void send_email(String usr, String pwd, String to, String subject, String body) throws SendFailedException, MessagingException{
		String password=pwd;
		String username=usr;

		String host = "smtp.gmail.com";
		String from=username;

		Properties props = System.getProperties();
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.host",host);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port",465);

		Session session = Session.getInstance(props);

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to, false));
		msg.setSubject(subject);
		msg.setText(body);

		Transport.send(msg,username,password);
	}



}
