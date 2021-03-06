package com.partytimeline.event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "events", collectionResourceRel = "events", itemResourceRel = "event")
public interface EventRepository extends CrudRepository<Event, Long> {
}
