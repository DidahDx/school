package sample.model;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendEmail {


    public boolean sendEmailMessage(String tSubject,String tMessage,String tReceiver){
        boolean check=false;
        try {
        String host="smtp.gmail.com";
        String user="projectuoe@gmail.com";
        String password="123@project";
        String from="projectuoe@gmail.com";
        String subject=tSubject;
        String message=tMessage;


            String to = tReceiver;
            String[] recipientList = to.split(",");
            InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
            int counter = 0;
            for (String recipient : recipientList) {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
                counter++;
            }

        boolean sessionDebug=false;

        Properties prop=System.getProperties();
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.host",host);
        prop.put("mail.smtp.port","587");
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.required","true");

        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); //java security

        Session  mailSession=Session.getDefaultInstance(prop,null);
        mailSession.setDebug(sessionDebug);

        Message ms=new MimeMessage(mailSession);
        ms.setFrom(new InternetAddress(from));              //setting senders address
            ms.setRecipients(Message.RecipientType.TO, recipientAddress); //receivers address
        ms.setSubject(subject);   //subject of email
        ms.setSentDate(new Date());
        ms.setText(message);  //email message

            Transport transport=mailSession.getTransport("smtp"); //this is what we send the message through
            transport.connect(host,user,password);  //used to for authentication of user and password
            transport.sendMessage(ms,ms.getAllRecipients());
            transport.close();

            System.out.println("email sent");
            check=true;

        } catch (Exception e) {
            e.printStackTrace();
            check=false;
        }

        return check;
    }



}
