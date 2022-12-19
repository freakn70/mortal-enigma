package com.mortalenigma.services;

import com.mortalenigma.entities.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    public Map<String, Object> sendEmail(Email email) {
        Map<String, Object> result;
        try {
            MimeMessage message = sender.createMimeMessage();
            message.setFrom(new InternetAddress("contact@nesscoloura.com", "Nesscoloura"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
            message.setReplyTo(new Address[]{new InternetAddress("contact@nesscoloura.com")});

            message.setSubject(email.getSubject());
            message.setContent(email.getBody(), "text/html");

            sender.send(message);

            result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Email sent successfully.");
        } catch (Exception e) {
            result = new HashMap<>();
            result.put("success", false);
            result.put("message", "Error: " + e.getMessage());
        }
        return result;

        /*
        replyTo = replyTo == null ? "customercare@zivame.com" : replyTo;
        // Create a default MimeMessage object.
        MimeMessage message = sender.createMimeMessage();

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from, "Zivame"));
        // Set To: header field of the header.
        for (String toEmail : to.split("[,;]")) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        }
        Address[] addrs = { new InternetAddress(replyTo) };
        message.setReplyTo(addrs);

        // Set Subject: header field
        message.setSubject(subject);

        // Send the actual HTML message, as big as you like
        message.setContent(body, "text/html");

        // SMTP API header
        if (category != null) {
            Map<String, Object> smtpHeader = new HashMap<>();
            smtpHeader.put("category", new String[] { category });
            try {
                String smtpHeaderString = " " + MAPPER.writeValueAsString(smtpHeader);
                message.addHeader("X-SMTPAPI", smtpHeaderString);
            } catch (JsonProcessingException e) {
                log.error("unable to prepare X-SMTPAPI JSON", e);
            }
        }

        // Send message
        sender.send(message);
        * */
    }
}
