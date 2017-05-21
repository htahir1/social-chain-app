package com.partytimeline.event_image;

import com.partytimeline.event.Event;
import com.partytimeline.event.EventRepository;
import com.partytimeline.user.User;
import com.partytimeline.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@RepositoryRestController
@RequestMapping(value = "/partytimeline/api/v1/event_image")
public class EventImageController {
    private final UserRepository userRepository;
    private final EventImageRepository eventImageRepository;
    private final EventRepository eventRepository;
    private final String upload_path_original = "images/original_images/";
    private final String upload_path_small = "images/small_images/";

    private final Logger log = LoggerFactory.getLogger(this.getClass());



    @Autowired
    public EventImageController(UserRepository userRepository, EventImageRepository eventImageRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventImageRepository = eventImageRepository;
        this.eventRepository = eventRepository;
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public ResponseEntity addEventImageMetadata(@RequestBody EventImageDTO eventImageDTO) {
        if (eventImageDTO != null) {
            EventImage eventImage = new EventImage(eventImageDTO.getId(), eventImageDTO.getCaption(), "", "", eventImageDTO.getDate_taken());

            if (eventImageDTO.getEvent_member_id() != null) {
                User user = userRepository.findOne(eventImageDTO.getEvent_member_id());
                if (user != null) {
                    eventImage.setUser(user);
                }
            }

            if (eventImageDTO.getEvent_id() != null) {
                Event event = eventRepository.findOne(eventImageDTO.getEvent_id());
                if (event != null) {
                    eventImage.setEvent(event);
                }
            }
            eventImageRepository.save(eventImage);

            log.info("addEventImageMetadata succeeded with event id: {}", eventImageDTO.getEvent_id());

            return ResponseEntity.ok(eventImage.getId());
        }
        log.info("addEventImageMetadata failed with event id: {}", eventImageDTO.getEvent_id());
        return ResponseEntity.badRequest().build();
    }


    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public ResponseEntity addEventImage(@RequestParam("id") Long event_image_id,
                                        @RequestParam(value="event_id") Long event_id,
                                        @RequestParam(value="event_member_id") Long event_member_id,
                                        @RequestParam(value="quality") String quality,
                                        @RequestParam("event_image_file") MultipartFile file) {
            if (file != null) {
                EventImage eventImage = eventImageRepository.findByUserAndEventAndId(userRepository.findOne(event_member_id),
                        eventRepository.findOne(event_id),
                        event_image_id);

                File new_file = handleFileUpload(file, quality);
                if (new_file != null) {
                    if (quality.equals("small")) {
                        eventImage.setPathSmall(new_file.getPath());
                    }
                    else {
                        eventImage.setPath_original(new_file.getPath());
                    }
                    log.info("addEventImage succeeded for event_image_id: {} with image path: {}", event_image_id, new_file.getPath());
                    eventImageRepository.save(eventImage);
                    return ResponseEntity.ok(eventImage.getId());
                }
            }

        log.info("addEventImage failed with event_image_id: {}", event_image_id);
        return ResponseEntity.badRequest().build();
    }

    private String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public File handleFileUpload(MultipartFile file, String quality) {
        if (!file.isEmpty()) {
            String path = upload_path_original;
            try {
                if (quality.equals("small")) {
                    path = upload_path_small;
                }
                File new_file = new File(path + file.getOriginalFilename());

                Boolean mkdirs = new_file.mkdirs();
                log.info("handleFileUpload mkdirs: {}", mkdirs);

                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new_file));
                stream.write(bytes);
                stream.close();
                log.info("handleFileUpload file created successfully after upload");
                return new_file;
            } catch (Exception e) {
                log.info("handleFileUpload file creation failed with exception: {}", e.toString());
                return null;
            }
        } else {
            log.info("handleFileUpload file creation failed");
            return null;
        }
    }
}
