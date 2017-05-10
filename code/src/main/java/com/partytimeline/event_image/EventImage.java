package com.partytimeline.event_image;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.partytimeline.core.BaseEntity;
import com.partytimeline.event.Event;
import com.partytimeline.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event_images")
public class EventImage extends BaseEntity {
    @Size(max = 500)
    private String caption;

    @NotNull
    @Column(unique = true)
    @Size(max = 500)
    private String path;

    @NotNull
    @Column(unique = true)
    @Size(max = 128)
    private String path_small;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_taken;

    @ManyToOne(cascade = CascadeType.ALL)
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    protected EventImage() {
        super();
    }

    public EventImage(String caption, String path, String path_small, Date date_taken) {
        this();
        this.caption = caption;
        this.path = path;
        this.path_small = path_small;
        this.date_taken = date_taken;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathSmall() {
        return path_small;
    }

    public void setPathSmall(String path_small) {
        this.path_small = path_small;
    }

    public Date getDateTaken() {
        return date_taken;
    }

    public void setDateTaken(Date date_taken) {
        this.date_taken = date_taken;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
