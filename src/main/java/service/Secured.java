package service;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;

/**
 *
 * @author Christopher G
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {

    /**
     *
     * @return
     */
    Role[] value() default {};
}
