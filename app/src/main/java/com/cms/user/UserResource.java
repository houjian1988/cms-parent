package com.cms.user;

import com.cms.common.utils.JAXBUtil;
import com.cms.dto.common.MapDto;
import com.cms.dto.user.UserDto;
import com.cms.user.domain.service.UserService;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.annotations.GZIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;


@Path("/user")
@Component
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserResource {
    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    @GET
    @Path("/{id}/{id}")
    @GZIP
    public Response user(@PathParam("id") Integer id) {
        LOG.info("Request[GET] user param id=" + id);
        UserDto user = userService.findByid(id);
        LOG.info("Response user:" + JAXBUtil.convertToXML(user));
        return Response.ok().entity(user).build();
    }

    @GET
    @Path("/{id}")
    @GZIP
    public Response findUserById(@PathParam("id") Integer id) {
        LOG.info("Request[GET] user param id=" + id);
        Map userMap = userService.findUserById(id);
        JSONObject jsonObj = new JSONObject(userMap);
//        MapDto mapDto = new MapDto(userMap);
//        LOG.info("Response user:" + JAXBUtil.convertToXML(mapDto));
        return Response.ok().entity(jsonObj.toString()).build();
    }

    @POST
    @Path("/login")
    @GZIP
    public Response adviserQuery(UserDto loginDto) {
        LOG.info("Request[POST] user param user=" + loginDto);
        UserDto user = userService.findByLoginName(loginDto);
        LOG.info("Response user:" + JAXBUtil.convertToXML(user));
        return Response.ok().entity(user).build();
    }

}
