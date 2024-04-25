package org.ispp4.cohabify.utils;

import org.ispp4.cohabify.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MailHelper {
    
    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromMailAddress;

    private JavaMailSender mailSender;

    public MailHelper(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Boolean sendVerificationEmail(User user) {
        try {
            MimeMessage mimeMessage = createVerificationMessage(user);

            mailSender.send(mimeMessage);
                
            return true;
            
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

    private MimeMessage createVerificationMessage(User user) throws AddressException, MessagingException {
        String verifyURL = frontendUrl + "/register/verify/" + user.getVerificationCode();
        String toAddress = user.getEmail();
        String subject = "Verifique su registro en Cohabify";
        String content = "<html>\n" +
                         "<body>\n" +
                         "<p>Estimado/a " + user.getUsername() + ",</p>\n" +
                         "<p>Por favor, entre en el siguiente enlace para completar su registro:</p>\n" +
                         "<p><a href=\"" + verifyURL + "\">" + verifyURL + "</a></p>\n" +
                         "<p>Muchas gracias por su confianza,</p>\n" +
                         "<p>Cohabify</p>\n" +
                         "</body>\n" +
                         "</html>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
     
        helper.setFrom(new InternetAddress(fromMailAddress));
        helper.setTo(new InternetAddress(toAddress));
        helper.setSubject(subject);
        helper.setText(content, true);

        return message;
    }

    public Boolean sendResetPasswordEmail(User user) {
        try {
            MimeMessage mimeMessage = createResetPasswordMessage(user);

            mailSender.send(mimeMessage);
                
            return true;
            
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

    private MimeMessage createResetPasswordMessage(User user) throws AddressException, MessagingException {
        String resetURL = frontendUrl + "/reset-password/" + user.getVerificationCode();
        String toAddress = user.getEmail();
        String subject = "Restablecimiento de su contraseña de Cohabify";
        String content = "<html>\n" +
                         "<body>\n" +
                         "<p>Estimado/a " + user.getUsername() + ",</p>\n" +
                         "<p>Por favor, entre en el siguiente enlace para restablecer su contraseña:</p>\n" +
                         "<p><a href=\"" + resetURL + "\">" + resetURL + "</a></p>\n" +
                         "<p>Si no ha pedido restablecer su contraseña, puede ignorar este mensaje.</p>\n" +
                         "<p>Muchas gracias por su confianza,</p>\n" +
                         "<p>Cohabify</p>\n" +
                         "</body>\n" +
                         "</html>";
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
     
        helper.setFrom(new InternetAddress(fromMailAddress));
        helper.setTo(new InternetAddress(toAddress));
        helper.setSubject(subject);
        helper.setText(content, true);

        return message;
    }

}
