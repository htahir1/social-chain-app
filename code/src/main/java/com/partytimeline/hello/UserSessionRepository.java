package com.partytimeline.hello;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "user_sessions", collectionResourceRel = "user_sessions", itemResourceRel = "user_session")
public interface UserSessionRepository extends CrudRepository<UserSession, Long> {

}
