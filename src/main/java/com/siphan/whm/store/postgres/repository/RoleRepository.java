package com.siphan.whm.store.postgres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.siphan.whm.store.postgres.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    
}
