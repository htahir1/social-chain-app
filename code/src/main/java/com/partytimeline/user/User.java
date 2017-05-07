package com.partytimeline.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.partytimeline.core.BaseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="app_user")
public class User extends BaseEntity {
    public static final PasswordEncoder PASSWORD_ENCODER =  new BCryptPasswordEncoder();

    public static enum ROLES {
        ADMIN("ROLE_ADMIN"),
        NORMAL("ROLE_USER");

        private final String text;

        private ROLES(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    @Size(min = 2, max = 140)
    private String firstName;

    @Size(min = 2, max = 140)
    private String lastName;

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 140)
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    @JsonIgnore
    private String[] roles;

    protected User() {
        super();
    }

    public User(String username, String firstName, String lastName, String password, String[] roles) {
        this();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        setPassword(password);
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
