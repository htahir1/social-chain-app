package com.partytimeline.user;

import java.util.Date;

public class UserDTO {
    private Long Id;
    private String email_address;
    private String name;
    private Integer expires_in;
    private Long expires_on;
    private Long access_token;

    public UserDTO(Long id, String email_address, String name, Integer expires_in, Long expires_on, Long access_token) {
        Id = id;
        this.email_address = email_address;
        this.name = name;
        this.expires_in = expires_in;
        this.expires_on = expires_on;
        this.access_token = access_token;
    }

    public UserDTO() {

    }

    public Long getAccess_token() {
        return access_token;
    }

    public void setAccess_token(Long access_token) {
        this.access_token = access_token;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public Long getExpires_on() {
        return expires_on;
    }

    public void setExpires_on(Long expires_on) {
        this.expires_on = expires_on;
    }

    public Date getExpires_on_date() {
        return new Date(expires_on);
    }

    public void setExpires_on_date(Date expires_on) {
        this.expires_on = expires_on.getTime();
    }
}
