package com.cinefamille.api.controller;

import com.cinefamille.api.dto.UserDto;
import com.cinefamille.api.dto.ReviewDto;
import com.cinefamille.api.dto.CreateUserRequest;
import com.cinefamille.api.service.ReviewService;
import com.cinefamille.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;

    public UserController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getUserReviews(@PathVariable Long id) {
        List<ReviewDto> reviews = reviewService.getReviewsByUser(id);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest user) {
        UserDto userCreated = userService.createUser(user);
        return ResponseEntity.status(201).body(userCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody CreateUserRequest user) {
        UserDto userUpdated = userService.updateUser(id, user);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}