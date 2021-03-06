package com.example.demo.service;

import javax.mail.MessagingException;

public interface EmailService {

    public void sendEmail(String to, String body, String topic);
    public void sendEmailAttachImage(String to, String body, String topic) throws MessagingException;
    public void sendHtmlEmail(String to,  String topic) throws MessagingException;
    public void sendTemplateEmail(String to, String topic) throws MessagingException;

}
