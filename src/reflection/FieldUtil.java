package reflection;

import java.lang.reflect.Field;

public class FieldUtil {

    public static void nullFieldToDefault(Object target) throws IllegalAccessException {
        Class<?> targetClass = target.getClass();
        for (Field field : targetClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(target) != null) {
                continue;
            }

            if (field.getType() == String.class) {
                field.set(target, "");
            } else if (field.getType() == Integer.class) {
                field.set(target, 0);
            }

        }
    }
}
