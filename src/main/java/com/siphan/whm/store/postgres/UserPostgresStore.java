package com.siphan.whm.store.postgres;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.siphan.whm.store.UserStore;
import com.siphan.whm.store.dtos.UserDto;
import com.siphan.whm.store.postgres.entity.UserEntity;
import com.siphan.whm.store.postgres.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserPostgresStore implements UserStore {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

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
        Example<UserEntity> example = Example.of(userEntity);
        Optional<UserEntity> userOptional = this.repository.findOne(example);
        
        if (userOptional.isPresent()) {
            return passwordEncoder.matches(password, userOptional.get().getPassword());
        }
        return false;
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
        List<UserEntity> userEntities = userDtos.stream().map(dto -> {
            UserEntity entity = new UserEntity(dto);
            // Hash password only if it's a new user or password is provided/changed
            if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            return entity;
        }).toList();
        try {
            this.repository.saveAll(userEntities);
            return true;
        } catch (DataAccessException e) {
            log.error("Error saving users: {}", e.getMessage());
            return false; 
        }
    }

    @Override
    public boolean deleteByListId(List<String> ids) {
        try {
            this.repository.deleteAllById(ids);
            return true;
        } catch (DataAccessException e) {
            log.error("Error deleting users by list of IDs: {}", e.getMessage());
            return false;
        }
    }
}
