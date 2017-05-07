package com.partytimeline.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.partytimeline.core.BaseEntity;
import com.partytimeline.event.Event;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="event_members")
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

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 140)
    private String email;

    @Size(min = 2, max = 140)
    private String name;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    @JsonIgnore
    private String[] roles;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Event> events;

    protected User() {
        super();
        events = new HashSet<>();
    }

    public User(String email, String name, String password, String[] roles) {
        this();
        this.email = email;
        this.name = name;
        setPassword(password);
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
        if (!event.getUsers().contains(this)) {
            event.addUser(this);
        }
    }
}
