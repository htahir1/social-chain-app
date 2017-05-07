package com.partytimeline.event_image;

import com.partytimeline.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class EventImage extends BaseEntity {
    @NotNull
    @Size(min = 2, max = 500)
    private String caption;

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 500)
    private String path;

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 128)
    private Date path_small;

    @NotNull
    private Date date_taken;

    public EventImage(String caption, String path, Date path_small, Date date_taken) {
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

    public Date getPathSmall() {
        return path_small;
    }

    public void setPathSmall(Date path_small) {
        this.path_small = path_small;
    }

    public Date getDateTaken() {
        return date_taken;
    }

    public void setDateTaken(Date date_taken) {
        this.date_taken = date_taken;
    }
}