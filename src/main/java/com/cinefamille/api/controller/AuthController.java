package com.cinefamille.api.controller;

import com.cinefamille.api.dto.LoginRequest;
import com.cinefamille.api.dto.RegisterRequest;
import com.cinefamille.api.model.User;
import com.cinefamille.api.repository.UserRepository;
import com.cinefamille.api.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        // vérifie si un utilisateur avec cet email existe déjà
        Optional<User> userFound = userRepository.findByEmail(request.getEmail());
        if (userFound.isPresent()) {
            return ResponseEntity.status(409).body(Map.of("error", "Email déjà utilisé"));
        }

        // crée un nouvel utilisateur
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // sauvegarde l'utilisateur et retourne 201
        userRepository.save(user);
        return ResponseEntity.status(201).body(Map.of("message", "Compte créé avec succès"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // cherche l'utilisateur par email
        Optional<User> userFound = userRepository.findByEmail(request.getEmail());
        if (userFound.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Identifiants invalides"));
        }

        // vérifie le mot de passe
        User user = userFound.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Identifiants invalides"));
        }

        // génère le token et retourne 200
        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }
}