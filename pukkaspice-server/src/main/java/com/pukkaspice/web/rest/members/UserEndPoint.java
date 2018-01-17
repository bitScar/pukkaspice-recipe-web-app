package com.pukkaspice.web.rest.members;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pukkaspice.web.common.jersey.ExceptionMapperImpl;
import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.common.security.SecurityController;
import com.pukkaspice.web.service.UserService;

@Component("UserEndPoint")
@Path("/user-service")
public class UserEndPoint {

    @Autowired
    private UserService userService;
    
    @Autowired
    private SecurityController securityController;
    
    
    @GET
    @Path("/current-user")
    @Produces(MediaType.APPLICATION_JSON)
    public UserSummary getCurrentUser() {
        return userService.getUserById(securityController.getCurrentUser().getUserId());
    }
    
    @POST
    @Path("/current-user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ExceptionHandler(ExceptionMapperImpl.class)
    public UserSummary saveCurrentUser(@Valid UserSummary userSummary) { // @Valid is handled by ValidationExceptionMapperImpl as specified in JerseyResourceConfig
        return userService.saveUser(userSummary);
    }
    
}
