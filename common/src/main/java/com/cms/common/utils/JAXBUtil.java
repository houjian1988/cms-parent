package com.cms.common.utils;

import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.jboss.resteasy.plugins.providers.jaxb.json.JettisonMappedContext;
import org.jboss.resteasy.plugins.providers.jaxb.json.JettisonMappedMarshaller;
import org.jboss.resteasy.plugins.providers.jaxb.json.JettisonMappedUnmarshaller;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class JAXBUtil {

    private JAXBUtil() {

    }

    /**
     * 默认转对象
     *
     * @param message 信息
     * @param type    类型
     * @param <T>     对象类型
     * @return 返回对象
     */
    public static <T> T convertJsonToObject(String message, Class<T> type) {
        return convertToObject(message, type, MediaType.APPLICATION_JSON);
    }

    public static <T> T convertXMLToObject(String message, Class<T> type) {
        return convertToObject(message, type, MediaType.APPLICATION_XML);
    }

    /**
     * 默认转message
     *
     * @param source 对象
     * @return JSON 格式字符串
     */
    public static String convertToMessage(Object source) {
        return convertToMessage(source, MediaType.APPLICATION_JSON);
    }

    public static String convertToXML(Object source) {
        return convertToMessage(source, MediaType.APPLICATION_XML);
    }

    public static String convertToJSONL(Object source) {
        return convertToMessage(source);
    }

    public static <T> T convertToObject(String message, Class<T> type, String mediaType) {
        StringReader reader = new StringReader(message);
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            if (mediaType.equals(MediaType.APPLICATION_JSON)) {
                context = JettisonMappedContext.newInstance(type);
                unmarshaller = new JettisonMappedUnmarshaller(context, new MappedNamespaceConvention());
            }
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }

    public static String convertToMessage(Object source, String mediaType) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(source.getClass());
            Marshaller marshaller = context.createMarshaller();
            if (mediaType.equals(MediaType.APPLICATION_JSON)) {
                context = JettisonMappedContext.newInstance(source.getClass());
                marshaller = new JettisonMappedMarshaller(context, new MappedNamespaceConvention());
            }
            marshaller.marshal(source, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
