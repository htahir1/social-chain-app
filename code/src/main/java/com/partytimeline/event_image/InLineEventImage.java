package com.partytimeline.event_image;

import com.partytimeline.event.Event;
import com.partytimeline.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "InLineEventImage", types = { EventImage.class })
public interface InLineEventImage {
    public String getCaption();

    public String getPath_original();

    public String getPath_small();

    @Value("#{target.getEvent().getId()}")
    public Long getEventId();

    @Value("#{target.getUser().getId()}")
    public Long getUserId();

    public Long getId();

    public String getOriginal_name();
}