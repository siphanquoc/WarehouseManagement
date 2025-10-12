package com.siphan.whm.store.postgres;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import com.siphan.whm.store.UserStore;
import com.siphan.whm.store.dtos.UserDto;
import com.siphan.whm.store.postgres.entity.UserEntity;
import com.siphan.whm.store.postgres.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserPostgresStore implements UserStore {
    private final UserRepository repository;

    @Override
    public UserDto findById(String id) {
       Optional<UserEntity> userOptional = this.repository.findById(id);
       return userOptional.isPresent() ? userOptional.get().toDto() : new UserDto();
    }

    @Override
    public UserDto findByUsername(String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        Example<UserEntity> example = Example.of(userEntity);
        Optional<UserEntity> userOptional = this.repository.findOne(example);
        return userOptional.isPresent() ? userOptional.get().toDto() : new UserDto();
    }

    @Override
    public  boolean login(String username, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        Example<UserEntity> example = Example.of(userEntity);
        Optional<UserEntity> userOptional = this.repository.findOne(example);
        return userOptional.isPresent();
    }

    @Override
    public List<UserDto> findAll() {
        return UserEntity.toDtos(this.repository.findAll());
    }

    @Override
    public List<UserDto> findByListId(List<String> ids) {
       return UserEntity.toDtos(this.repository.findAllById(ids));
    }

    @Override
    public boolean save(List<UserDto> userDtos) {
        List<UserEntity> userEntities = userDtos.stream().map(UserEntity::new).toList();
        try {
            this.repository.saveAll(userEntities);
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
