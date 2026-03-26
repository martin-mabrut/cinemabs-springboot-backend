package com.cinefamille.api.service;

import com.cinefamille.api.model.User;
import com.cinefamille.api.repository.UserRepository;
import com.cinefamille.api.dto.UserDto;
import com.cinefamille.api.dto.CreateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import com.cinefamille.api.exception.ResourceNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> toDto(user))
                .toList();
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> toDto(user))
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + id));
    }

    public UserDto createUser(CreateUserRequest userRequest) {
        User user = new User(
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getPassword()
        );
        User userSaved = userRepository.save(user);
        return toDto(userSaved);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDto updateUser(Long id, CreateUserRequest updatedUserRequest) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("utilisateur non trouvé avec l'id : " + id));
        existing.setUsername(updatedUserRequest.getUsername());
        existing.setEmail(updatedUserRequest.getEmail());
        existing.setPassword(updatedUserRequest.getPassword());
        User userUpdatedSaved = userRepository.save(existing);
        return toDto(userUpdatedSaved);
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}