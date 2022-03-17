package com.starry.starrycore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;

// @Service
public class MailUtil {

    @Value("${spring.mail.username}")
    private String mailFrom;

    // 多环境yaml配置下，会爆红 could not autowire. no beans of xxxx。
    @Autowired(required = false)
    private JavaMailSenderImpl mailSender;

    public void SendTextMail(String mailTo, String title, String body) throws Exception {
        if (!StringUtils.hasLength(mailTo)) {
            throw new Exception("邮件收件人不得为空");
        }
        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setFrom(mailFrom);
        mailMsg.setSubject(title);
        mailMsg.setText(body);
        if (mailTo.contains(",")) {
            mailMsg.setTo(mailTo.split(","));
        } else {
            mailMsg.setTo(mailTo);
        }
        mailSender.send(mailMsg);
    }

    public void SendMimeMail(String mailTo, String title, String htmlBody, File attachment) throws Exception {
        if (!StringUtils.hasLength(mailTo)) {
            throw new Exception("邮件收件人不得为空");
        }
        MimeMessage message;
        message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailFrom);
        helper.setSubject(title);
        helper.setTo(mailTo);
        helper.setText(htmlBody, true);
        if (attachment.exists()) {
            helper.addAttachment(attachment.getName(), attachment);
        }
        mailSender.send(message);
    }
}
