package com.partytimeline.event_image;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class EventImageDTO {
    private Long id;
    private String caption;
    private Date date_taken;
    private MultipartFile file;
    private Long event_id;
    private Long event_member_id;

    public EventImageDTO() {

    }

    public EventImageDTO(Long id, String caption, Date date_taken, MultipartFile file, Long event_id, Long event_member_id) {
        this.id = id;
        this.caption = caption;
        this.date_taken = date_taken;
        this.file = file;
        this.event_id = event_id;
        this.event_member_id = event_member_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(Date date_taken) {
        this.date_taken = date_taken;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public Long getEvent_member_id() {
        return event_member_id;
    }

    public void setEvent_member_id(Long event_member_id) {
        this.event_member_id = event_member_id;
    }
}
