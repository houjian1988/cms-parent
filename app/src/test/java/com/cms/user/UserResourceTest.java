package com.cms.user;

import com.cms.common.DataPrepareUtil;
import com.cms.common.MockDispatcher;
import com.cms.common.utils.JAXBUtil;
import com.cms.dto.common.MapDto;
import com.cms.dto.user.UserDto;
import com.cms.user.domain.service.UserService;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

public class UserResourceTest extends DataPrepareUtil {
    @Autowired
    private MockDispatcher mockDispatcher;
    @Autowired
    private UserService userService;

    @Test
    public void search() throws Exception {
        MockHttpRequest request = MockHttpRequest.get("/user/1");
        request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();
        mockDispatcher.getDispatcher("userResource", UserResource.class).invoke(request, response);
        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        String contentAsString = response.getContentAsString();
        JSONObject object = JAXBUtil.convertJsonToObject(response.getContentAsString(), JSONObject.class);
        Assert.assertEquals("houjian", object.toString());
    }

    @Test
    public void login() throws URISyntaxException, UnsupportedEncodingException {
        MockHttpRequest request = MockHttpRequest.post("/user/login");
        request.contentType(MediaType.APPLICATION_JSON);
        request.content(JAXBUtil.convertToMessage(new UserDto("houjian", "houjian")).getBytes("utf-8"));
        MockHttpResponse response = new MockHttpResponse();
        mockDispatcher.getDispatcher("userResource", UserResource.class).invoke(request, response);
        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        UserDto userDto = JAXBUtil.convertJsonToObject(response.getContentAsString(), UserDto.class);
        Assert.assertEquals("houjian", userDto.getLoginName());
    }
}
