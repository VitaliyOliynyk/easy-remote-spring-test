package eu.vitaliy.easyremote.marshal;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import java.lang.reflect.Method;

public class Marshaler {

    public static String marshal(Class<?>[] parameterTypes, Object[] objects) {
        XStream xStream = createXStream(parameterTypes);
        String marshaled = xStream.toXML(objects);
        return marshaled;
    }

    public static String marshal(Class<?> parameterType, Object objects) {
        XStream xStream = createXStream(new Class<?>[]{parameterType});
        String marshaled = xStream.toXML(objects);
        return marshaled;
    }

    public static Object[] unmarshal(Class<?>[] parameterTypes, String marshalazed) {
        XStream xStream = createXStream(parameterTypes);
        Object[] unmarshalazed = (Object[]) xStream.fromXML(marshalazed);
        return unmarshalazed;
    }

    public static Object unmarshal(Class<?>parameterType, String marshalazed) {
        XStream xStream = createXStream(new Class<?>[]{parameterType});
        Object unmarshalazed = xStream.fromXML(marshalazed);
        return unmarshalazed;
    }

    private static XStream createXStream(Class<?>[] parameterTypes) {
        XStream xStream = new XStream(new DomDriver());
        for (Class<?> parameterType : parameterTypes) {
            xStream.alias(parameterType.getName(), parameterType);
        }

        xStream .addPermission(NoTypePermission.NONE); //forbid everything
        xStream .addPermission(NullPermission.NULL);   // allow "null"
        xStream .addPermission(PrimitiveTypePermission.PRIMITIVES); // allow primitive types
        xStream.allowTypesByWildcard(new String[]{"**"});

        return xStream;
    }
}
