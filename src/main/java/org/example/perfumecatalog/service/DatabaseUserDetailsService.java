package org.example.perfumecatalog.service;

import org.example.perfumecatalog.model.Role;
import org.example.perfumecatalog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String adminEmail = "admin@gmail.com";
        if(adminEmail.equals(username)) {
            return adminUser(adminEmail);
        }
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("There is no user with given email"));
    }

    private static UserDetails adminUser(String adminEmail) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Set.of(Role.ADMIN);
            }

            @Override
            public String getPassword() {
                return "$2a$12$Mzvbw7zjpe8ozFfr0HuiZe1xBVNITMQY4TMYT0D2d4aY9ilEvohN."; // 12345
            }

            @Override
            public String getUsername() {
                return adminEmail;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
