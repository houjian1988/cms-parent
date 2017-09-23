package com.cms.common;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.*;
import java.util.*;
import java.util.Map.Entry;

public class RestMultiPortEmbeddedServer extends SpringJUnit4ClassRunner {

    private List<TJWSEmbeddedJaxrsServer> servers = new ArrayList<TJWSEmbeddedJaxrsServer>();

    public RestMultiPortEmbeddedServer(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        int[] ports = findAnnotationValueByClass(Ports.class).value();
        Class[] resourceClasses = findAnnotationValueByClass(Resources.class).value();

        Map<Integer, List<Class>> maps = new HashMap<Integer, List<Class>>();

        for (int i = 0; i < ports.length; i++) {
            int port = ports[i];
            if (null == maps.get(port)) {
                maps.put(port, new ArrayList<Class>());
            }
            maps.get(port).add(resourceClasses[i]);
        }
        for (Entry<Integer, List<Class>> entry : maps.entrySet()) {
            startServer(new TJWSEmbeddedJaxrsServer(), entry.getKey(), entry.getValue().toArray(new Class[entry.getValue().size()]));
        }
        try {
            super.run(notifier);
        } finally {
            for (TJWSEmbeddedJaxrsServer server : servers) {
                server.stop();
            }
        }
    }

    private void startServer(TJWSEmbeddedJaxrsServer server, int port, Class[] resourceClasses) {
        server.setPort(port);
        List<Class> actualResourceClasses = server.getDeployment().getActualResourceClasses();
        Collections.addAll(actualResourceClasses, resourceClasses);
        server.start();
        servers.add(server);
    }

    private <T> T findAnnotationValueByClass(Class<T> annotationClass) {
        for (Annotation annotation : getTestClass().getAnnotations()) {
            if (annotation.annotationType().equals(annotationClass)) {
                return (T) annotation;
            }
        }
        throw new IllegalStateException(String.format("Can't find %s on test class: %s", annotationClass, getTestClass()));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public static @interface Resources {
        public Class[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public static @interface Ports {
        public int[] value();
    }
}
