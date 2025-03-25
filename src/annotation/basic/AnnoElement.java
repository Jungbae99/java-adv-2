package annotation.basic;

import util.MyLogger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AnnoElement {

    // 자바의 기본적인 타입만 선언할 수 있다.
    String value();
    int count() default 0;
    String[] tags() default {};

    // MyLogger data(); 다른 타입은 적용 X
    Class<? extends MyLogger> annoData() default MyLogger.class;

}
