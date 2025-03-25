package annotation.basic;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,  ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Documented
public @interface AnnoMeta {
}
