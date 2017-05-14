package com.partytimeline.user_session;

import com.partytimeline.core.BaseEntity;
import com.partytimeline.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "user_sessions")
public class UserSession extends BaseEntity {

    public final static String ColumnExpiresOn = "expires_on";
    public final static String ColumnFacebookToken = ColumnId;
    public final static String ColumnUserId = "user_id";

    public final static String FieldUserId = "user";

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnUserId, nullable = false)
    private User user;

    @Id
    @NotNull
    @Column(name = ColumnFacebookToken, nullable = false)
    private String facebookToken;

    @NotNull
    @Column(name = ColumnExpiresOn, nullable = false)
    private Date expiresOn;

    // TODO: add a column for the rights the user has


    protected UserSession() {
        super();
    }

    public UserSession(String facebookToken, User user, Date expiresOn) {
        super();
        this.user = user;
        this.expiresOn = expiresOn;
        this.facebookToken = facebookToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }
}
