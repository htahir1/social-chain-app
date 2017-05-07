package com.partytimeline.core;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    private Date date_created;

    private Date date_modified;

    @Version
    private Long version;

    protected BaseEntity() {
        id = null;
    }

    public Long getId() {
        return id;
    }

    @PrePersist
    protected void onCreate() {
        Date new_date = new Date();
        this.date_created = new_date;
        this.date_created = new_date;
    }

    @PreUpdate
    protected void onUpdate() {
        this.date_modified = new Date();
    }
}