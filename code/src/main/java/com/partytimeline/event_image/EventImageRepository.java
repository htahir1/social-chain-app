package com.partytimeline.event_image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventImageRepository extends CrudRepository<EventImage, Long> {
}
