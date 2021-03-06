package com.example.demo.service.impl;

import com.example.demo.config.SpringEmailConfig;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String body, String topic){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("quyeta2ubqn@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        System.out.println("send email....");
    }

    public void sendEmailAttachImage(String to, String body, String topic) throws MessagingException {
        // Create a Mime MailMessage.
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(topic);
        helper.setText(body);

        // Add attachment
        FileSystemResource file = new FileSystemResource(new File( System.getProperty("user.dir") + "\\src\\main\\resources\\static\\avatar.png"));
        helper.addAttachment(file.getFilename(), file);

        // Send Message!
        javaMailSender.send(message);
        System.out.println("send email attach image....");
    }

    public void sendHtmlEmail(String to, String topic) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(topic);

        // Add html content
        String htmlMsg = "<h1 style=\"color:green\">Hello World</h1> <img src='http://www.apache.org/images/asf_logo_wide.gif'>";
        message.setContent(htmlMsg, "text/html");

        // Send Message!
        javaMailSender.send(message);

        System.out.println("send email HTML....");
    }

    public void sendTemplateEmail(String to, String topic) throws MessagingException {
        // Create a Mime MailMessage.
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(topic);

        // Prepare the evaluation context
        final Context context = new Context();
        context.setVariable("name", "to");
        context.setVariable("project_name", "spring-email-with-thymeleaf Demo");

        // Create the HTML body using Thymeleaf
        final String htmlContent = SpringEmailConfig.getTemplateEngine().process("mail-template.html", context);
        message.setContent(htmlContent, "text/html");

        // Send Message!
        javaMailSender.send(message);
    }
}
