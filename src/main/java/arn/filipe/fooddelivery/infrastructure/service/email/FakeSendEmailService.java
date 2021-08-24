package arn.filipe.fooddelivery.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeSendEmailService extends SMTPSendEmailService {

    @Override
    public void send(Message message) {
        String body = processTemplate(message);

        log.info("[FAKE E-MAIL] To: {}\n{}", message.getRecipients(), body);
    }
}
