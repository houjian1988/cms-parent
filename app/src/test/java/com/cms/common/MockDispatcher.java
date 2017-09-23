package com.cms.common;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.jboss.resteasy.spi.ResourceFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MockDispatcher implements ApplicationContextAware {

    private ApplicationContext context;

    public MockDispatcher() {
    }

    public Dispatcher getDispatcher(String beanName, Class<?> classes) {
        ResourceFactory rf = new SpringResourceFactory(beanName, context, classes);
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addResourceFactory(rf);
        return dispatcher;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return this.context;
    }

}
