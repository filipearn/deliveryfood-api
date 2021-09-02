package arn.filipe.fooddelivery.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class User {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime registrationDate;

    @ManyToMany
    @JoinTable(name = "user_team",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams = new HashSet<>();

    public boolean passwordMatch(String newPassword){
        return this.getPassword().equals(newPassword);
    }

    public boolean passwordNotMatch(String newPassword){
        return !passwordMatch(newPassword);
    }

    public boolean associateTeam(Team team){
        return this.getTeams().add(team);
    }

    public boolean disassociateTeam(Team team){
        return this.getTeams().remove(team);
    }

    public boolean isNew() {
        return getId() == null;
    }
}
