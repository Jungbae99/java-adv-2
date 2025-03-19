package reflection;

import java.lang.reflect.Constructor;

public class ConstructV1 {
    public static void main(String[] args) throws ClassNotFoundException {

        Class<?> aClass = Class.forName("reflection.data.BasicData");
        System.out.println("===== constructors() =====");
        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("constructor = " + constructor);
        }

        System.out.println("===== declaredConstructors() =====");
        Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            System.out.println("declaredConstructor = " + declaredConstructor);
        }
    }
}
