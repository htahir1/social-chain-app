package com.partytimeline.core;

import com.partytimeline.helper.AutowireHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;

@Configuration
public class RestConfig extends RepositoryRestConfigurerAdapter {

    @Autowired
    private Validator validator;

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }

    @Bean
    public AutowireHelper autowireHelper(){
        return AutowireHelper.getInstance();
    }

//    @Override
//    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        config.exposeIdsFor(User.class);
//        config.exposeIdsFor(Event.class);
//        config.exposeIdsFor(EventImage.class);
//    }
}
