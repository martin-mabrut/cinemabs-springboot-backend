package com.cinefamille.api.service;

import com.cinefamille.api.dto.CreateUserRequest;
import com.cinefamille.api.dto.UserDto;
import com.cinefamille.api.exception.ResourceNotFoundException;
import com.cinefamille.api.model.User;
import com.cinefamille.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // -------------------------------------------------------------------------
    // getAllUsers()
    // -------------------------------------------------------------------------

    @Test
    void getAllUsers_shouldReturnListOfDtos() {
        // ARRANGE
        // TODO : crée un User (utilise le constructeur avec username, email, password)
        User user = new User("username", "email@email.com", "password");
        // TODO : dis au mock que findAll() retourne List.of(cet user)
        when(userRepository.findAll()).thenReturn(List.of(user));

        // ACT
        // TODO : appelle userService.getAllUsers() et stocke le résultat
        List<UserDto> result = userService.getAllUsers();

        // ASSERT
        // TODO : vérifie que la liste a 1 élément
        assertThat(result).hasSize(1);
        // TODO : vérifie que le username du premier élément est correct
        assertThat(result.get(0).getUsername()).isEqualTo("username");
    }

    @Test
    void getAllUsers_shouldReturnEmptyList_whenNoUsers() {
        // ARRANGE
        // TODO : dis au mock que findAll() retourne une liste vide
        when(userRepository.findAll()).thenReturn(List.of());

        // ACT
        // TODO : appelle userService.getAllUsers()
        List<UserDto> result = userService.getAllUsers();

        // ASSERT
        // TODO : vérifie que le résultat est vide
        assertThat(result).isEmpty();
    }

    // -------------------------------------------------------------------------
    // getUserById()
    // -------------------------------------------------------------------------

    @Test
    void getUserById_shouldReturnDto_whenUserExists() {
        // ARRANGE
        // TODO : crée un User et dis au mock que findById(1L) retourne Optional.of(cet user)
        User user = new User("username", "email@email.com", "password");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // ACT
        // TODO : appelle userService.getUserById(1L)
        UserDto result = userService.getUserById(1L);

        // ASSERT
        // TODO : vérifie au moins un champ du DTO retourné
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("email@email.com");
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        // ARRANGE
        // TODO : dis au mock que findById(99L) retourne Optional.empty()
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT + ACT
        // TODO : vérifie qu'une ResourceNotFoundException est lancée avec un message contenant "99"
        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

    }

    // -------------------------------------------------------------------------
    // createUser()
    // -------------------------------------------------------------------------

    @Test
    void createUser_shouldReturnDto_withCorrectFields() {
        // ARRANGE
        // TODO : crée un CreateUserRequest avec username, email, password
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("username");
        createUserRequest.setEmail("email@email.com");
        createUserRequest.setPassword("password");

        // TODO : crée un User "saved" avec les mêmes valeurs + id=1L
        User saved = new User("username", "email@email.com", "password");

        // TODO : dis au mock que save(any()) retourne cet user
        when(userRepository.save(any())).thenReturn(saved);

        // ACT
        // TODO : appelle userService.createUser(request)
        UserDto result = userService.createUser(createUserRequest);

        // ASSERT
        // TODO : vérifie le username et l'email du DTO retourné
        assertThat(result.getUsername()).isEqualTo("username");
        assertThat(result.getEmail()).isEqualTo("email@email.com");
    }

    // -------------------------------------------------------------------------
    // deleteUser()
    // -------------------------------------------------------------------------

    @Test
    void deleteUser_shouldCallRepositoryDeleteById() {
        // ACT
        // TODO : appelle userService.deleteUser(1L)
        userService.deleteUser(1L);

        // ASSERT
        // TODO : vérifie que deleteById(1L) a bien été appelé sur le repository
        verify(userRepository).deleteById(1L);
    }

    // -------------------------------------------------------------------------
    // updateUser()
    // -------------------------------------------------------------------------

    @Test
    void updateUser_shouldReturnUpdatedDto_whenUserExists() {
        // Ce test est un peu plus long — décompose bien les étapes :
        // ARRANGE
        // TODO : crée un User existant avec username="martin", email="martin@test.com"
        User user = new User("martin", "martin@test.com", "password");
        // TODO : dis au mock que findById(1L) retourne Optional.of(cet user)
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // TODO : crée un CreateUserRequest avec les nouvelles valeurs (username="martin2", email="martin2@test.com", password="newpass")
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("martin2");
        createUserRequest.setEmail("martin2@test.com");
        createUserRequest.setPassword("newpass");
        // TODO : crée un User "updated" avec les nouvelles valeurs + id=1L
        User updated = new User("martin2", "martin2@test.com", "newpass");
        updated.setId(1L);
        // TODO : dis au mock que save(any()) retourne cet user "updated"
        when(userRepository.save(any())).thenReturn(updated);

        // ACT
        // TODO : appelle userService.updateUser(1L, request)
        UserDto result = userService.updateUser(1L, createUserRequest);

        // ASSERT
        // TODO : vérifie que le username du DTO est "martin2"
        assertThat(result.getUsername()).isEqualTo("martin2");
        // TODO : vérifie que l'email du DTO est "martin2@test.com"
        assertThat(result.getEmail()).isEqualTo("martin2@test.com");
    }

    @Test
    void updateUser_shouldThrowException_whenUserNotFound() {
        // ARRANGE
        // TODO : dis au mock que findById(99L) retourne Optional.empty()
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT + ACT
        // TODO : vérifie qu'une ResourceNotFoundException est lancée
        assertThatThrownBy(() -> userService.updateUser(99L, new CreateUserRequest()))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}