package arn.filipe.fooddelivery.domain.model;

import arn.filipe.fooddelivery.core.validation.Groups;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class State {

    @NotNull(groups = Groups.StateId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;
}
