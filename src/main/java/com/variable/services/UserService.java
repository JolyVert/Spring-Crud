package com.variable.services;

import com.variable.dtos.LoginUserDto;
import com.variable.dtos.RegisterUserDto;
import com.variable.dtos.UpdateUserDto;
import com.variable.entities.Role;
import com.variable.entities.User;
import com.variable.entities.enums.RoleEnum;
import com.variable.repositories.RoleRepository;
import com.variable.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User createAdministrator(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User();
                user.setName(input.getName());
                user.setEmail(input.getEmail());
                user.setPassword(passwordEncoder.encode(input.getPassword()));
                user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public User updateUser(UpdateUserDto input, Long id) {

        User updateUser = userRepository.findById(id).get();
        updateUser.setName(input.getName());
        updateUser.setEmail(input.getEmail());
        updateUser.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(updateUser);
    }

    public void deleteUser(long id) {
        User deleteUser = userRepository.findById(id).get();

        userRepository.delete(deleteUser);
    }
}