package com.partytimeline.event_image;

import java.io.File;
import java.util.Date;

public class EventImageDTO {
    private String caption;
    private String path;
    private String path_small;
    private Date date_taken;
    private File image_file;
    private Long event_id;

    public EventImageDTO() {

    }

    public EventImageDTO(String caption, String path, String path_small, Date date_taken, File image_file, Long event_id) {
        this.caption = caption;
        this.path = path;
        this.path_small = path_small;
        this.date_taken = date_taken;
        this.image_file = image_file;
        this.event_id = event_id;
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

    public String getPath_small() {
        return path_small;
    }

    public void setPath_small(String path_small) {
        this.path_small = path_small;
    }

    public Date getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(Date date_taken) {
        this.date_taken = date_taken;
    }

    public File getImage_file() {
        return image_file;
    }

    public void setImage_file(File image_file) {
        this.image_file = image_file;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }
}
