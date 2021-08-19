package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CustomizedJpaRepository<Permission, Long>{
}
