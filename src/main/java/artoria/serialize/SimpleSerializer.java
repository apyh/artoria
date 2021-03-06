package artoria.serialize;

import artoria.util.Assert;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Serializer simple implement by jdk.
 * @author Kahle
 */
public class SimpleSerializer implements Serializer<Object> {

    @Override
    public void serialize(Object object, OutputStream outputStream) throws IOException {
        Assert.notNull(object, "Parameter \"object\" must not null. ");
        Assert.notNull(outputStream, "Parameter \"outputStream\" must not null. ");
        if (!(object instanceof Serializable)) {
            String className = SimpleSerializer.class.getSimpleName();
            String objectName = object.getClass().getName();
            throw new IllegalArgumentException(
                    className + " requires a Serializable payload but received an object of type [" + objectName + "]. "
            );
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

}
