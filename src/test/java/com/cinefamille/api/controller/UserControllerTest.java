package com.cinefamille.api.controller;

import com.cinefamille.api.dto.CreateUserRequest;
import com.cinefamille.api.dto.UserDto;
import com.cinefamille.api.service.ReviewService;
import com.cinefamille.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    // -------------------------------------------------------------------------
    // GET /api/users
    // -------------------------------------------------------------------------

    @Test
    void getAllUsers_shouldReturn200WithList() throws Exception {
        // ARRANGE
        // TODO : crée un UserDto et dis au mock que userService.getAllUsers() le retourne dans une liste
        UserDto userDto = new UserDto(1L, "martin", "test@email.com");
        when(userService.getAllUsers()).thenReturn(List.of(userDto));

        // ACT + ASSERT
        // TODO : simule GET /api/users
        mockMvc.perform(get("/api/users"))
        // TODO : vérifie status 200
                .andExpect(status().isOk())
        // TODO : vérifie que $[0].username a la bonne valeur
                .andExpect(jsonPath("$[0].username").value("martin"));
    }

    // -------------------------------------------------------------------------
    // GET /api/users/{id}
    // -------------------------------------------------------------------------

    @Test
    void getUserById_shouldReturn200_whenUserExists() throws Exception {
        // ARRANGE
        // TODO : crée un UserDto et dis au mock que userService.getUserById(1L) le retourne
        UserDto userDto = new UserDto(1L, "martin", "test@email.com");
        when(userService.getUserById(1L)).thenReturn(userDto);

        // ACT + ASSERT
        // TODO : simule GET /api/users/1
        mockMvc.perform(get("/api/users/1"))
        // TODO : vérifie status 200
                .andExpect(status().isOk())
        // TODO : vérifie $.username
                .andExpect(jsonPath("$.username").value("martin"));
    }

    // -------------------------------------------------------------------------
    // POST /api/users
    // -------------------------------------------------------------------------

    @Test
    void createUser_shouldReturn201_whenRequestIsValid() throws Exception {
        // ARRANGE
        // TODO : crée un CreateUserRequest avec username, email, password
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("martin");
        request.setEmail("test@email.com");
        request.setPassword("password");

        // TODO : crée un UserDto "created" avec id=1L
        UserDto created = new UserDto(1L, "martin", "test@email.com");

        // TODO : dis au mock que userService.createUser(any()) retourne ce DTO
        when(userService.createUser(any())).thenReturn(created);

        // ACT + ASSERT
        // TODO : simule POST /api/users avec le body en JSON
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        // TODO : vérifie status 201
                .andExpect(status().isCreated())
        // TODO : vérifie $.username
                .andExpect(jsonPath("$.username").value("martin"));
    }

    // -------------------------------------------------------------------------
    // PUT /api/users/{id}
    // -------------------------------------------------------------------------

    @Test
    void updateUser_shouldReturn200_whenRequestIsValid() throws Exception {
        // ARRANGE
        // TODO : crée un CreateUserRequest avec les nouvelles valeurs
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("martin2");
        request.setEmail("test2@test.com");
        request.setPassword("password2");

        // TODO : crée un UserDto "updated" avec les nouvelles valeurs
        UserDto updated = new UserDto(1L, "martin2", "test2@email.com");

        // TODO : dis au mock que userService.updateUser(eq(1L), any()) retourne ce DTO
        //        (hint: eq(1L) permet de matcher précisément l'id, any() pour le body)
        when(userService.updateUser(eq(1L), any())).thenReturn(updated);

        // ACT + ASSERT
        // TODO : simule PUT /api/users/1 avec le body en JSON
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        // TODO : vérifie status 200
                .andExpect(status().isOk())
        // TODO : vérifie $.username
                .andExpect(jsonPath("$.username").value("martin2"));
    }

    // -------------------------------------------------------------------------
    // DELETE /api/users/{id}
    // -------------------------------------------------------------------------

    @Test
    void deleteUser_shouldReturn204() throws Exception {
        // ACT + ASSERT
        // TODO : simule DELETE /api/users/1
        mockMvc.perform(delete("/api/users/1"))
        // TODO : vérifie status 204
                .andExpect(status().isNoContent());
    }
}