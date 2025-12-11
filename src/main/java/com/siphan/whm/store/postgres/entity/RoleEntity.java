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

import com.siphan.whm.store.dtos.RoleDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class RoleEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "parent_role_id")
    private String parentRoleId;

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

    public RoleEntity(RoleDto dto) {
        BeanUtils.copyProperties(dto, this);
    }

    public RoleDto toDto() {
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(this, roleDto);
        return roleDto;
    }

    public static List<RoleDto> toDtos(Iterable<RoleEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map((RoleEntity::toDto)).collect(Collectors.toList());
    }

}