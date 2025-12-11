package com.siphan.whm.store.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private String phone;
    private String roleId;
    private Boolean isActive;
    private Instant lastLogin;
}
