package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.Order;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;


public class MailBookService {

    public MailBookService() {
    }

    public void sendEmail(Order order) throws AddressException, MessagingException, IOException {
        try{
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("estockiiitb@gmail.com", "estock123");
                }
            });
            String emailId  = order.getUser_id().getEmail();
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("estockiiitb@gmail.com", false));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
            msg.setSubject("Custom EBook Order Number: "+order.getOrderId());
            msg.setContent("Greetings ", "text/html");
            msg.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            String text = "Hello " + order.getUser_id().getFirstName() + " ," + "\n" + "Thank you for ordering from CustomEBook. We would like to inform you " +
                    "that your CustomEBook order with Order Number:"+order.getOrderId()+" purchased on " + order.getOrderDate() + " has been settled processed.\nPlease find attached the PDF File of your book: "
                    +order.getCustomEBookName()+" \n\nThank You, \nCustomEBook";
            messageBodyPart.setContent(text, "text/plain");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            MimeBodyPart attachPart = new MimeBodyPart();

            attachPart.attachFile(order.getLocation());
            multipart.addBodyPart(attachPart);
            msg.setContent(multipart);
            Transport.send(msg);
        }
        catch (Exception e){
            throw e;
        }

    }
}
