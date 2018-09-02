package artoria.serialize;

import artoria.util.Assert;

import java.io.*;
import java.util.logging.Logger;

/**
 * Serialize tools.
 * @author Kahle
 */
public class SerializeUtils {
    private static Logger log = Logger.getLogger(SerializeUtils.class.getName());
    private static final int INITIAL_SIZE = 128;
    private static Serializer<Object> serializer;
    private static Deserializer<Object> deserializer;

    public static Serializer<Object> getSerializer() {
        if (serializer != null) {
            return serializer;
        }
        synchronized (Serializer.class) {
            if (serializer != null) {
                return serializer;
            }
            setSerializer(new SimpleSerializer());
            return serializer;
        }
    }

    public static void setSerializer(Serializer<Object> serializer) {
        Assert.notNull(serializer, "Parameter \"serializer\" must not null. ");
        synchronized (Serializer.class) {
            log.info("Set serializer: " + serializer.getClass().getName());
            SerializeUtils.serializer = serializer;
        }
    }

    public static Deserializer<Object> getDeserializer() {
        if (deserializer != null) {
            return deserializer;
        }
        synchronized (Deserializer.class) {
            if (deserializer != null) {
                return deserializer;
            }
            setDeserializer(new SimpleDeserializer());
            return deserializer;
        }
    }

    public static void setDeserializer(Deserializer<Object> deserializer) {
        Assert.notNull(deserializer, "Parameter \"deserializer\" must not null. ");
        synchronized (Deserializer.class) {
            log.info("Set deserializer: " + deserializer.getClass().getName());
            SerializeUtils.deserializer = deserializer;
        }
    }

    public static Object clone(Object obj) {
        byte[] data = SerializeUtils.serialize(obj);
        return SerializeUtils.deserialize(data);
    }

    public static byte[] serialize(Object object) {
        Assert.notNull(object, "Parameter \"object\" must not null. ");
        ByteArrayOutputStream baos = new ByteArrayOutputStream(INITIAL_SIZE);
        try {
            SerializeUtils.serialize(object, baos);
        }
        catch (IOException e) {
            String msg = "Failed to serialize object of type : ";
            throw new IllegalArgumentException(msg + object.getClass(), e);
        }
        return baos.toByteArray();
    }

    public static Object deserialize(byte[] bytes) {
        Assert.notEmpty(bytes, "Parameter \"bytes\" must not empty. ");
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return SerializeUtils.deserialize(bais);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Failed to deserialize object. ", e);
        }
    }

    public static void serialize(Object object, OutputStream outputStream) throws IOException {
        Assert.notNull(object, "Parameter \"object\" must not null. ");
        Assert.notNull(outputStream, "Parameter \"outputStream\" must not null. ");
        getSerializer().serialize(object, outputStream);
    }

    public static Object deserialize(InputStream inputStream) throws IOException {
        Assert.notNull(inputStream, "Parameter \"inputStream\" must not null. ");
        return getDeserializer().deserialize(inputStream);
    }

}
