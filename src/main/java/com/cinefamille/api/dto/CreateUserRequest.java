package com.cinefamille.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank(message = "Veuillez renseigner un nom d'utilisateur")
    private String username;

    @NotBlank(message = "Veuillez renseigner une adresse email")
    @Email(message = "Veuillez renseigner une adresse email valide")
    private String email;

    @NotBlank(message = "Veuillez renseigner un mot de passe")
    private String password;

    public CreateUserRequest() {
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setUsername(String newUsername) { this.username = newUsername; }
    public void setEmail(String newEmail) { this.email = newEmail; }
    public void setPassword(String newPassword) { this.password = newPassword; }
}
