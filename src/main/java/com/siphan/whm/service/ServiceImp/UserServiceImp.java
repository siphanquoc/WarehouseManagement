package com.siphan.whm.service.ServiceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.siphan.whm.service.UserService;
import com.siphan.whm.store.UserStore;
import com.siphan.whm.store.dtos.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserStore userStore;

    @Override
    public UserDto findById(String id) {
      return this.userStore.findById(id);
    }

    @Override
    public UserDto findByUsername(String username) {
        return this.userStore.findByUsername(username);
    }

    @Override
    public boolean login(String username, String password) {
        return this.userStore.login(username, password);
    }

    @Override
    public List<UserDto> findAll() {
        return this.userStore.findAll();
    }

    @Override
    public List<UserDto> findByListId(List<String> ids) {
        return this.userStore.findByListId(ids);
    }

    @Override
    public boolean save(List<UserDto> userDtos) {
        return this.userStore.save(userDtos);
    }

    @Override
    public boolean deleteByListId(List<String> ids) {
       return this.userStore.deleteByListId(ids);
    }
    
}
