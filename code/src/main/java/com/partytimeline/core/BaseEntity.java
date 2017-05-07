package com.partytimeline.core;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    private Long date_created;

    private Long date_modified;

    @Version
    private Long version;

    protected BaseEntity() {
        id = null;
    }

    public Long getId() {
        return id;
    }
}