package org.example.perfumecatalog.service;

import org.example.perfumecatalog.dto.RegisterUserDto;
import org.example.perfumecatalog.model.Role;
import org.example.perfumecatalog.model.User;
import org.example.perfumecatalog.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public boolean isLoggedInUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().contains(Role.ADMIN);
    }

    public void registerUser(RegisterUserDto userDto) {
        if(!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            throw new IllegalArgumentException(
                    "Entered passwords are not same %s != %s".formatted(
                            userDto.getPassword(), userDto.getPasswordConfirmation()
                    )
            );
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

}
