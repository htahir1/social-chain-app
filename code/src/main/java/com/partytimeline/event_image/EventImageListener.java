package com.partytimeline.event_image;

import com.partytimeline.helper.AutowireHelper;
import com.partytimeline.helper.S3Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PreRemove;

@Component
public class EventImageListener {
    @Autowired
    private S3Wrapper s3Wrapper;

    private String turnPathToKey(String path) {
        return path.substring(path.indexOf(".com/")+5, path.length());
    }

    @PreRemove
    public void eventImagePostRemove(EventImage ei) {
        AutowireHelper.autowire(this, this.s3Wrapper);

        System.out.println("Listening Event Image Post Remove : " + ei.getId());
        if (ei.getPath_original() != null && !ei.getPath_original().equals("")) {
            s3Wrapper.delete(turnPathToKey(ei.getPath_original()));
        }
        if (ei.getPath_small() != null && !ei.getPath_small().equals("")) {
            s3Wrapper.delete(turnPathToKey(ei.getPath_small()));
        }
    }
}