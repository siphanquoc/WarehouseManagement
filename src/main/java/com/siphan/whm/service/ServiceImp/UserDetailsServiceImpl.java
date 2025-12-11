package com.siphan.whm.service.ServiceImp;

import com.siphan.whm.service.UserService;
import com.siphan.whm.store.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userService.findByUsername(username);
        if (userDto == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Assuming no roles/authorities are explicitly managed yet, return an empty list
        return new User(userDto.getUsername(), userDto.getPassword(), new ArrayList<>());
    }
}
