package com.example.authservice.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean emailConfirmed = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public UUID getId() {return id;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public boolean isEmailConfirmed() {return emailConfirmed;}
    public Set<Role> getRoles() {return roles;}

    public void setId(UUID id) {this.id = id;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setEmailConfirmed(boolean emailConfirmed) {this.emailConfirmed = emailConfirmed;}
    public void setRoles(Set<Role> roles) {this.roles = roles;}
}
