package com.pukkaspice.web.common.jersey;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.validation.ValidationFeature;

public class JerseyResourceConfig extends ResourceConfig {
    /**
     * Register JAX-RS application components.
     */
    public JerseyResourceConfig() {
        this.packages(true, "com.pukkaspice.web.rest");

        this.register(RequestContextFilter.class);
        this.register(JacksonFeature.class);
        this.register(MultiPartFeature.class);
        // can also add bean validation here


        // this.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // this.register(componentClass)

        // register(RequestContextFilter.class);
        // register(PodcastRestService.class);
        // register(JacksonFeature.class);
        // register(LoggingResponseFilter.class);
        // register(CORSResponseFilter.class);
        

        // validation
        this.register(ValidationFeature.class); // says that we are using avliation
        this.register(ValidationExceptionMapperImpl.class); // default ValidationExceptionMapper mapper for validations (befoe method executaion)
        this.register(ExceptionMapperImpl.class); // general mapper for all exaception thrown in methods (after method execution)
        this.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
//        this.property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
    }
}
