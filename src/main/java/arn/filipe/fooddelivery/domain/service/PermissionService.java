package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.EntityInUseException;
import arn.filipe.fooddelivery.domain.exception.PermissionNotFoundException;
import arn.filipe.fooddelivery.domain.model.Permission;
import arn.filipe.fooddelivery.domain.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionService {

    public static final String PERMISSION_IN_USE = "Permission with id %d can't be removed. Resource in use.";

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> listAll(){
        return permissionRepository.findAll();
    }

    public Permission save(Permission permission){
        return permissionRepository.save(permission);
    }

    public Permission verifyIfExistsOrThrow(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));
    }

    @Transactional
    public void delete(Long id){
        try{
            permissionRepository.deleteById(id);
            permissionRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PermissionNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(PERMISSION_IN_USE, id));
        }
    }
}
