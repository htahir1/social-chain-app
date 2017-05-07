package com.partytimeline.event;

import com.partytimeline.core.BaseEntity;
import com.partytimeline.event_image.EventImage;
import com.partytimeline.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event extends BaseEntity {
    @NotNull
    @Size(min = 2, max = 140)
    private String name;

    @NotNull
    @Size(min = 2, max = 500)
    private String description;

    @NotNull
    private Date start_time;

    @NotNull
    private Date end_time;

    @ManyToMany(mappedBy = "events", cascade = CascadeType.ALL)
    private Set<User> users;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<EventImage> event_images;

    protected Event() {
        super();
        users = new HashSet<>();
        event_images = new HashSet<>();
    }
    public Event(String name, String description, Date start_time, Date end_time) {
        this.name = name;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return start_time;
    }

    public void setStartTime(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEndTime() {
        return end_time;
    }

    public void setEndTime(Date end_time) {
        this.end_time = end_time;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        if (!user.getEvents().contains(this)) {
            user.addEvent(this);
        }
    }

    public Set<EventImage> getImages() {
        return event_images;
    }

    public void addImage(EventImage event_image) {
        event_images.add(event_image);
        if (event_image.getEvent() != this) {
            event_image.setEvent(this);
        }
    }
}
