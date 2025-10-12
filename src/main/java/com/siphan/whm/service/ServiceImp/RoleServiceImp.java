package com.siphan.whm.service.ServiceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.siphan.whm.service.RoleService;
import com.siphan.whm.store.RoleStore;
import com.siphan.whm.store.dtos.RoleDto;

@Service
public class RoleServiceImp implements RoleService{
    private RoleStore roleStore;

    @Override
    public RoleDto findById(String id) {
        return this.roleStore.findById(id);
    }

    @Override
    public List<RoleDto> findAll() {
       return this.roleStore.findAll();
    }

    @Override
    public List<RoleDto> findByListId(List<String> ids) {
       return this.roleStore.findByListId(ids);
    }

    @Override
    public boolean save(List<RoleDto> roleDtos) {
       return this.roleStore.save(roleDtos);
    }

    @Override
    public boolean deleteByListId(List<String> ids) {
        return this.roleStore.deleteByListId(ids);
    }
    
}
