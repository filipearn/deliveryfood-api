package arn.filipe.fooddelivery.infrastructure.service.email;

import arn.filipe.fooddelivery.core.email.EmailProperties;
import arn.filipe.fooddelivery.domain.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SMTPSendEmailService implements SendEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(Message message) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(emailProperties.getSender());
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setSubject(message.getSubject());
            helper.setText(message.getBody(), true);


            mailSender.send(mimeMessage);
        } catch (Exception e){
            throw new EmailException("Was not possible to send the email", e);
        }

    }
}
