package com.partytimeline.user;

import java.util.Date;

public class UserDTO {
    private String email_address;
    private String name;
    private Long expires_on;
    private String access_token;
    private Long user_id;

    public UserDTO(String email_address, String name, Long expires_on, String access_token, Long user_id) {
        this.email_address = email_address;
        this.name = name;
        this.expires_on = expires_on;
        this.access_token = access_token;
        this.user_id = user_id;
    }

    public UserDTO() {

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
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
