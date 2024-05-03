package com.yayla.secondhand.secondhandbackend.service.mail;

import com.yayla.secondhand.secondhandbackend.config.properties.MailProperties;
import com.yayla.secondhand.secondhandbackend.model.vo.SendMailVo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    private void sendMail(SendMailVo sendMailVo) throws MessagingException {
        log.info("Send mail has started to: {}", sendMailVo.getTo());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(sendMailVo.getTo());
        mimeMessageHelper.setFrom(mailProperties.getMailFromAddress());
        mimeMessageHelper.setSubject(sendMailVo.getSubject());
        mimeMessageHelper.setText(sendMailVo.getContent(), true);
        mailSender.send(mimeMessage);
        log.info("Send mail has finished to: {}", sendMailVo.getTo());
    }

    @Async
    public void sendRegisterConfirmationMail(String to, UUID token) throws Exception {
        String subject = "Secondhand - Signup Confirmation";
        String confirmationLink = mailProperties.getFeUrl() + "/api/v1/auth/signup-confirm?token=" + token;
        String htmlBody = this.retrieveMailTemplate("account-confirm-mail.html");
        htmlBody = htmlBody.replaceAll(Pattern.quote("${confirmLink}"), confirmationLink);
        SendMailVo sendMailVo = SendMailVo.builder()
                .to(to)
                .subject(subject)
                .content(htmlBody).build();
        this.sendMail(sendMailVo);
    }

    private String retrieveMailTemplate(String filename) throws IOException {
        InputStream inputStream = new ClassPathResource("/mail/" + filename).getInputStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8);
        return writer.toString();
    }
}
