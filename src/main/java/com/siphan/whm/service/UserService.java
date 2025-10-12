package com.siphan.whm.service;

import java.util.List;

import com.siphan.whm.store.dtos.UserDto;

public interface UserService {
    UserDto findById(String id);

    UserDto findByUsername(String username);

    boolean login(String username, String password);

    List<UserDto> findAll();

    List<UserDto> findByListId(List<String> ids);

    boolean save(List<UserDto> userDtos);

    boolean deleteByListId(List<String> ids);
}
