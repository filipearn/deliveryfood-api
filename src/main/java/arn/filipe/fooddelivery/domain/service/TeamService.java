package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.TeamNotFoundException;
import arn.filipe.fooddelivery.domain.model.PaymentWay;
import arn.filipe.fooddelivery.domain.model.Permission;
import arn.filipe.fooddelivery.domain.model.Restaurant;
import arn.filipe.fooddelivery.domain.model.Team;
import arn.filipe.fooddelivery.domain.repository.PermissionRepository;
import arn.filipe.fooddelivery.domain.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    public static final String TEAM_IN_USE = "Team with id %d can't be removed. Resource in use.";

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PermissionService permissionService;

    public List<Team> listAll(){
        return teamRepository.findAll();
    }

    public Team findById(Long id){
        return verifyIfExistsOrThrow(id);
    }

    @Transactional
    public Team save(Team team){
        return teamRepository.save(team);
    }

    @Transactional
    public void associatePermission(Long teamId, Long permissionId){
        Team team = verifyIfExistsOrThrow(teamId);
        Permission permission  = permissionService.verifyIfExistsOrThrow(permissionId);

        team.associatePermission(permission);
    }

    @Transactional
    public void disassociatePermission(Long teamId, Long permissionId){
        Team team = verifyIfExistsOrThrow(teamId);
        Permission permission  = permissionService.verifyIfExistsOrThrow(permissionId);

        team.disassociatePermission(permission);
    }

    @Transactional
    public void delete(Long id){
        try{
            teamRepository.deleteById(id);
            teamRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new TeamNotFoundException(id);
        }catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(TEAM_IN_USE, id));
        }
    }

    public Team verifyIfExistsOrThrow(Long id){
        return teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }
}
