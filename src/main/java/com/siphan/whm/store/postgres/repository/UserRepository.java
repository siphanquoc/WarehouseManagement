package com.siphan.whm.store.postgres.repository;

import com.siphan.whm.store.postgres.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String>{
}
