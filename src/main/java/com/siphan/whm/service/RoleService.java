package com.siphan.whm.service;

import java.util.List;

import com.siphan.whm.store.dtos.RoleDto;

public interface RoleService {
    RoleDto findById(String id);

    List<RoleDto> findAll();

    List<RoleDto> findByListId(List<String> ids);

    boolean save(List<RoleDto> roleDtos);

    boolean deleteByListId(List<String> ids);
}
