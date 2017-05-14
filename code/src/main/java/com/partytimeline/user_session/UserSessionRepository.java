package com.partytimeline.user_session;

import com.partytimeline.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "user_sessions", collectionResourceRel = "user_sessions", itemResourceRel = "user_session")
public interface UserSessionRepository extends CrudRepository<UserSession, Long> {
    @RestResource(rel = "user", path = "user")
    List<UserSession> findByUser(@Param("user") User user);
}
