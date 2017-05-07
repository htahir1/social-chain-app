package com.partytimeline.event_image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "event_images", collectionResourceRel = "event_images", itemResourceRel = "event_image")
public interface EventImageRepository extends CrudRepository<EventImage, Long> {
}
