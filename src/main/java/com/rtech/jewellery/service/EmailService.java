package com.rtech.jewellery.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPdfEmail(String toEmail,String customerName, byte[] pdfBytes) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Sale Details");
        helper.setText("Attached Receipt.");

        helper.addAttachment("receipt_"+customerName+".pdf", new ByteArrayDataSource(pdfBytes,"application/pdf"));

        javaMailSender.send(message);
    }
}
