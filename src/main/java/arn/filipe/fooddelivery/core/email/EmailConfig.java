package arn.filipe.fooddelivery.core.email;

import arn.filipe.fooddelivery.domain.service.SendEmailService;
import arn.filipe.fooddelivery.infrastructure.service.email.FakeSendEmailService;
import arn.filipe.fooddelivery.infrastructure.service.email.SMTPSendEmailService;
import arn.filipe.fooddelivery.infrastructure.service.email.SandBoxSendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public SendEmailService sendMail(){
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeSendEmailService();
            case SMTP:
                return new SMTPSendEmailService();
            case SANDBOX:
                return new SandBoxSendEmailService();
            default:
                return null;
        }

    }
}
