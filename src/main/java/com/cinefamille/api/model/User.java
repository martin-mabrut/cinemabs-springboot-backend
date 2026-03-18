package com.cinefamille.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Veuillez renseigner un nom d'utilisateur")
    private String username;

    @NotBlank(message = "Veuillez renseigner une adresse email")
    @Email(message = "Veuillez renseigner une adresse email valide")
    private String email;

    @NotBlank(message = "Veuillez renseigner un mot de passe")
    private String password;

    private boolean isPublic;

    private List<String> followed = new ArrayList<>();

    public User() {
    }

    public User (String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isPublic() { return isPublic; }
    public List<String> getFollowed() { return followed; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    public void togglePublic() { this.isPublic = !this.isPublic; }

    public void addFollowed(String username) { this.followed.add(username); }
    public void removeFollowed(String username) { this.followed.remove(username); }
}