package com.partytimeline.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.partytimeline.core.BaseEntity;
import com.partytimeline.event.Event;
import com.partytimeline.event_image.EventImage;
import com.partytimeline.user_session.UserSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User extends BaseEntity {
    @NotNull
    @Id
    @Column(name = ColumnId, nullable = false)
    private Long id;

    public final static String ColumnEmail = "email";
    public final static String ColumnName = "name";

    public static final PasswordEncoder PASSWORD_ENCODER =  new BCryptPasswordEncoder();

    public enum ROLES {
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
    @Column(name = ColumnEmail, unique = true)
    @Size(min = 2, max = 140)
    private String email;

    @NotNull
    @Column(name=ColumnName)
    @Size(min = 2, max = 140)
    private String name;

    @JsonIgnore
    private String password;

    @NotNull
    @JsonIgnore
    private String[] roles;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Event> events;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<EventImage> event_images;

    @OneToMany(mappedBy = UserSession.FieldUserId, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserSession> userSessions;

    protected User() {
        super();
        events = new HashSet<>();
        event_images = new HashSet<>();
        userSessions = new HashSet<>();
    }

    public User(Long id, String email, String name, String[] roles) {
        this();
        this.id = id;
        this.email = email;
        this.name = name;
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

    public Set<EventImage> getImages() {
        return event_images;
    }

    public void addImage(EventImage event_image) {
        event_images.add(event_image);
        if (event_image.getUser() != this) {
            event_image.setUser(this);
        }
    }

    public Set<UserSession> getUserSessions() {
        return userSessions;
    }

    public void addUserSession(UserSession session) {
        userSessions.add(session);
        if (session.getUser() != this) {
            session.setUser(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
