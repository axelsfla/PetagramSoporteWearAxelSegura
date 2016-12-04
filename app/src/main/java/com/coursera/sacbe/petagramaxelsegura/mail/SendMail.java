package com.coursera.sacbe.petagramaxelsegura.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import android.os.AsyncTask;

/**
 * Created by Sacbe on 09/09/2016.
 */

public class SendMail {

    private String email_from="courseraunam.dev@gmail.com";
    private String email_to="axelsfla@hotmail.com";
    private String email_cc;
    private String name;
    private String subject = "Comentario tarea coursera android";
    private String textMessage;
    private Session session = null;

    public String getEmail_from() {
        return email_from;
    }

    public String getEmail_to() {
        return email_to;
    }

    public String getEmail_cc() {
        return email_cc;
    }

    public void setEmail_cc(String email_cc) {
        this.email_cc = email_cc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public boolean sendMail(){
        boolean resultado = true;
        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("courseraunam.dev@gmail.com", "c06rs3r4");
                }
            });

            RetreiveFeedTask task = new RetreiveFeedTask();
            task.execute();

        }catch (Exception ex){
            resultado = false;

        }

        return resultado;

    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                //Se envía el comentario
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(getEmail_from()));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getEmail_to()));
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(getEmail_cc()));
                message.setSubject(getSubject());
                message.setContent(getTextMessage(), "text/html; charset=utf-8");
                Transport.send(message);
                //Se envia la respuesta automática del comentario
                Message messageRes = new MimeMessage(session);
                messageRes.setFrom(new InternetAddress(getEmail_from()));
                messageRes.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getEmail_cc()));
                messageRes.setSubject("Comentario recibido.");
                messageRes.setContent("Recibimos tu comentario.<br><br>Pronto nos pondrémos en contacto. Gracias!<br><br><br> Atte. Coursera Unam Dev", "text/html; charset=utf-8");
                Transport.send(messageRes);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }



}
