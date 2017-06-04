package com.partytimeline.event_image;

import com.partytimeline.event.Event;
import com.partytimeline.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "event_images", collectionResourceRel = "event_images", itemResourceRel = "event_image")
public interface EventImageRepository extends CrudRepository<EventImage, Long> {
    EventImage findByUserAndEventAndId(@Param("user") User user, @Param("event") Event event, @Param("id") Long id);

    List<EventImage> findByEventId(@Param("event_id") Long event_id);
}
