package com.partytimeline.event_image;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "InLineEventImage", types = { EventImage.class })
public interface InLineEventImage {
    public String getCaption();

    public String getPath_original();

    public String getPath_small();

    @JsonProperty("event_id")
    @Value("#{target.getEvent().getId()}")
    public Long getEventId();

    @JsonProperty("user_id")
    @Value("#{target.getUser().getId()}")
    public Long getUserId();

    public Long getId();

    public String getOriginal_name();
}