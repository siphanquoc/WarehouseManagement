package com.siphan.whm.store.postgres;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.siphan.whm.store.RoleStore;
import com.siphan.whm.store.dtos.RoleDto;
import com.siphan.whm.store.postgres.entity.RoleEntity;
import com.siphan.whm.store.postgres.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RolePostgresStore implements RoleStore{
    private final RoleRepository repository;

    @Override
    public RoleDto findById(String id) {
        Optional<RoleEntity> roleOptional = this.repository.findById(id);
        return roleOptional.isPresent() ? roleOptional.get().toDto() : new RoleDto();
    }
    
    @Override
    public List<RoleDto> findAll() {
        return RoleEntity.toDtos(this.repository.findAll());
    }

    @Override
    public List<RoleDto> findByListId(List<String> ids) {
        return RoleEntity.toDtos(this.repository.findAllById(ids));
    }

    @Override
    public boolean save(List<RoleDto> roleDtos) {
        List<RoleEntity> roleEntities = roleDtos.stream().map(RoleEntity::new).toList();
        try {
            this.repository.saveAll(roleEntities);
            return true;
        } catch (Exception e) {
            return false; 
        }
    }

    @Override
    public boolean deleteByListId(List<String> ids) {
        try {
            this.repository.deleteAllById(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
