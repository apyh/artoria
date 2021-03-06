package artoria.convert;

import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import artoria.util.ArrayUtils;
import artoria.util.Assert;
import artoria.util.ClassUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Type convert tools.
 * @author Kahle
 */
public class TypeConvertUtils {
    private static final Map<Class<?>, TypeConverter> CONVERTERS;
    private static Logger log = LoggerFactory.getLogger(TypeConvertUtils.class);

    static {
        CONVERTERS = new ConcurrentHashMap<Class<?>, TypeConverter>();
        TypeConvertUtils.register(Object.class, new ObjectConverter());
        TypeConvertUtils.register(String.class, new StringConverter());
        TypeConvertUtils.register(Number.class, new NumberConverter());
        TypeConvertUtils.register(Date.class, new DateConverter());
    }

    public static TypeConverter unregister(Class<?> clazz) {
        Assert.notNull(clazz, "Parameter \"clazz\" must not null. ");
        TypeConverter remove = CONVERTERS.remove(clazz);
        if (remove != null) {
            String removeClassName = remove.getClass().getName();
            String clazzName = clazz.getName();
            log.info("Unregister \"{}\" to \"{}\". ", removeClassName, clazzName);
        }
        return remove;
    }

    public static void register(Class<?> clazz, TypeConverter converter) {
        Assert.notNull(clazz, "Parameter \"clazz\" must not null. ");
        Assert.notNull(converter, "Parameter \"converter\" must not null. ");
        String converterClassName = converter.getClass().getName();
        String clazzName = clazz.getName();
        log.info("Register \"{}\" to \"{}\". ", converterClassName, clazzName);
        CONVERTERS.put(clazz, converter);
    }

    public static Object convert(Object source, Class<?> target) {
        if (source == null) { return null; }
        Class<?> clazz = source.getClass();
        target = ClassUtils.getWrapper(target);
        clazz = ClassUtils.getWrapper(clazz);
        if (target.isAssignableFrom(clazz)) { return source; }
        do {
            TypeConverter converter = CONVERTERS.get(clazz);
            if (converter != null) {
                source = converter.convert(source, target);
                if (source == null) { return null; }
                if (target.isAssignableFrom(source.getClass())) {
                    return source;
                }
            }
            Class<?>[] interfaces = clazz.getInterfaces();
            if (ArrayUtils.isEmpty(interfaces)) { continue; }
            for (Class<?> inter : interfaces) {
                converter = CONVERTERS.get(inter);
                if (converter == null) { continue; }
                source = converter.convert(source, target);
                if (target.isAssignableFrom(source.getClass())) {
                    return source;
                }
            }
        } while ((clazz = clazz.getSuperclass()) != null);
        return source;
    }

}
