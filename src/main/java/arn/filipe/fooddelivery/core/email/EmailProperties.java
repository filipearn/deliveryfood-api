package arn.filipe.fooddelivery.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("fooddelivery.email")
public class EmailProperties {

    private Sandbox sandbox = new Sandbox();

    @NotNull
    private String sender;

    @NotNull
    private EmailImpl impl = EmailImpl.FAKE;

    public enum EmailImpl{
        FAKE, SMTP, SANDBOX
    }

    @Getter
    @Setter
    public class Sandbox {
        private String recipient;
    }
}
