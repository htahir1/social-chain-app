package com.partytimeline.event_image;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.partytimeline.core.BaseEntity;
import com.partytimeline.event.Event;
import com.partytimeline.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import static com.partytimeline.core.Constants.DATE_FORMAT_STRING;

@Entity
@Table(name = "event_images")
public class EventImage extends BaseEntity {
    @NotNull
    @Id
    @Column(name = ColumnId, nullable = false)
    private Long id;

    @Size(max = 500)
    private String caption;

    @NotNull
    @Column(unique = true)
    @Size(max = 500)
    private String path_original;

    @NotNull
    @Column(unique = true)
    @Size(max = 500)
    private String path_small;


    @Size(max = 500)
    private String original_name;

    @NotNull
    @JsonFormat(pattern = DATE_FORMAT_STRING)
    private Date date_taken;

    @ManyToOne(cascade = CascadeType.ALL)
    private Event event;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    protected EventImage() {
        super();
        id = null;
    }

    public EventImage(Long id, String caption, String path_original, String path_small, Date date_taken) {
        this();
        this.id = id;
        this.caption = caption;
        this.path_original = path_original;
        this.path_small = path_small;
        this.date_taken = date_taken;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPath_original() {
        return path_original;
    }

    public void setPath_original(String path_original) {
        this.path_original = path_original;
    }

    public String getPath_small() {
        return path_small;
    }

    public void setPath_small(String path_small) {
        this.path_small = path_small;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }
}
