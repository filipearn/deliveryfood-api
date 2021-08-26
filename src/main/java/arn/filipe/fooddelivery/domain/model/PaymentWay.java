package arn.filipe.fooddelivery.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity(name = "payment_way")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentWay {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @UpdateTimestamp
    private OffsetDateTime updateDate;
}
