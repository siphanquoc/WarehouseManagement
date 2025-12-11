package com.siphan.whm.store.postgres.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import com.siphan.whm.store.dtos.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "last_login")
    private Instant lastLogin;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    public UserEntity(UserDto dto) {
        BeanUtils.copyProperties(dto, this);
    }

    public UserDto toDto() {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(this, userDto);
        return userDto;
    }

    public static List<UserDto> toDtos(Iterable<UserEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map((UserEntity::toDto)).collect(Collectors.toList());
    }
}
