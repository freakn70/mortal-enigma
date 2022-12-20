package com.mortalenigma.services;

import com.mortalenigma.entities.Email;
import com.mortalenigma.utilities.Helper;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Helper helper;

    @Value("${email.id}")
    private String email_id;

    @Value("${email.signature}")
    private String signature;

    @Async
    void send(Email email) {
        try {
            MimeMessage message = sender.createMimeMessage();
            message.setFrom(new InternetAddress(email_id, "Nesscoloura"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
            message.setReplyTo(new Address[]{new InternetAddress(email_id)});
            message.setSubject(email.getSubject());
            message.setContent(email.getBody(), "text/html");
            sender.send(message);

            log.info("Email sent to " + email.getTo());
        } catch (Exception e) {
            log.error("Error not sent: " + e.getMessage());
        }
    }

    public Map<String, Object> sendEmailVerificationOtp(String email) {
        String OTP = helper.generateOtp(6, false, false);
        String body = "<div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>" +
                "<div style='margin:50px auto;width:70%;padding:20px 0'>" +
                "<div style='border-bottom:1px solid #eee'>" +
                "<a href='' style='font-size:1.4em;color: #212121;text-decoration:none;font-weight:600'>Nesscoloura</a>" +
                "</div>" +
                "<p style='font-size:1.1em'>Hello,</p>" +
                "<p>Thank you for using Nesscoloura. Use the following OTP to complete registration process. OTP is valid for 5 minutes</p>" +
                "<h2 style='position: absolute;background: #212121;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 8px;'>" +
                OTP + "</h2>" +
                "<br /><br /><p>Regards,<br />Nesscoloura</p>" +
                "</div></div>";

        send(new Email(email, "Email verification", body, null));

        Map<String, Object> result = new HashMap<>();
        result.put("otp", OTP);
        result.put("message", "OTP sent to registered email.");
        return result;
    }
}
