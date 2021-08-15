package arn.filipe.fooddelivery.domain.model;

import arn.filipe.fooddelivery.core.validation.Groups;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Entity
@Data
public class City {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Valid
    @ConvertGroup(from=Default.class, to= Groups.StateId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private State state;

}
