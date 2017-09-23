package com.cms.common.utils;

import com.cms.common.exception.RestClientException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
public class RestClient {
    private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);

    public void post(String url, Object entity) {
        this.post(url, entity, MediaType.APPLICATION_JSON);
    }

    public void post(String url, Object entity, String mediaType) {
        Invocation.Builder request = requestBuild(url, mediaType);
        Response response = null;
        try {
            response = request.post(Entity.entity(entity, mediaType));
        } catch (Exception e) {
            String message = "Post Request URL:" + url;
            LOG.error(message, e);
            throw new RestClientException(message, e);
        }
        this.checkStatus(response);
    }

    public <T> T post(String url, Object entity, Class<T> entityType) {
        return this.post(url, entity, MediaType.APPLICATION_JSON, entityType);
    }

    public <T> T post(String url, Object entity, String mediaType, Class<T> entityType) {
        Invocation.Builder request = requestBuild(url, mediaType);
        Response response = null;
        try {
            response = request.post(Entity.entity(entity, mediaType));
        } catch (Exception e) {
            String message = "Post Request URL:" + url;
            LOG.error(message, e);
            throw new RestClientException(message, e);
        }
        this.checkStatus(response);
        return response.readEntity(entityType);
    }

    public void put(String url, Object entity) {
        this.put(url, entity, MediaType.APPLICATION_JSON);
    }

    public void put(String url, Object entity, String mediaType) {
        Invocation.Builder request = requestBuild(url, mediaType);
        Response response = null;
        try {
            response = request.put(Entity.entity(entity, mediaType));
        } catch (Exception e) {
            String message = "Put Request URL:" + url;
            LOG.error(message, e);
            throw new RestClientException(message, e);
        }
        this.checkStatus(response);
    }

    public <T> T put(String url, Object entity, Class<T> entityType) {
        return this.put(url, entity, MediaType.APPLICATION_JSON, entityType);
    }

    public <T> T put(String url, Object entity, String mediaType, Class<T> entityType) {
        Invocation.Builder request = requestBuild(url, mediaType);
        Response response;
        try {
            response = request.put(Entity.entity(entity, mediaType));
        } catch (Exception e) {
            String message = "Put Request URL:" + url;
            LOG.error(message, e);
            throw new RestClientException(message, e);
        }
        this.checkStatus(response);
        return response.readEntity(entityType);
    }

    public void delete(String url) {
        this.delete(url, MediaType.APPLICATION_JSON);
    }

    public void delete(String url, String mediaType) {
        Invocation.Builder request = requestBuild(url, mediaType);
        Response response;
        try {
            response = request.delete();
        } catch (Exception e) {
            String message = "Delete Request URL:" + url;
            LOG.error(message, e);
            throw new RestClientException(message, e);
        }
        this.checkStatus(response);
    }

    public <T> T delete(String url, Class<T> entityType) {
        return this.delete(url, MediaType.APPLICATION_JSON, entityType);
    }

    public <T> T delete(String url, String mediaType, Class<T> entityType) {
        Invocation.Builder request = requestBuild(url, mediaType);
        Response response;
        try {
            response = request.delete();
        } catch (Exception e) {
            String message = "Delete Request URL:" + url;
            LOG.error(message, e);
            throw new RestClientException(message, e);
        }
        return response.readEntity(entityType);
    }

    public <T> T get(String url, Class<T> entityType) {
        return this.get(url, entityType, MediaType.APPLICATION_JSON);
    }

    public <T> T get(String url, Class<T> entityType, String mediaType) {
        Invocation.Builder request = requestBuild(url, mediaType);
        Response response;
        try {
            response = request.get();
        } catch (Exception e) {
            String message = "Get Request URL:" + url;
            LOG.error(message, e);
            throw new RestClientException(message, e);
        }
        this.checkStatus(response);
        return response.readEntity(entityType);
    }

    private void checkStatus(Response response) {
        int status = response.getStatus();
        if (status == Response.Status.OK.getStatusCode()) {
            return;
        }
        String message = "error[" + status + "]" + response.readEntity(String.class);
        LOG.error(message);
        if (status == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new NotFoundException(message);
        }
        if (status == Response.Status.NOT_ACCEPTABLE.getStatusCode()) {
            throw new NotAcceptableException(message);
        }
        if (status == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new RestClientException(message);
        }
    }

    protected Invocation.Builder requestBuild(String url, String mediaType) {
        LOG.debug("RestClient url:" + url);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        Invocation.Builder request = target.request();
        request.accept(mediaType);
        request.header("Content-Type", mediaType);
        request.acceptEncoding("UTF-8");
        return request;
    }

}
