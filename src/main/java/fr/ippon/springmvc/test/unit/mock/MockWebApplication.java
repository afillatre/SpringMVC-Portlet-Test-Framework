package fr.ippon.springmvc.test.unit.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configures a mock {@link org.springframework.web.context.WebApplicationContext}.  Each test class (or parent class) using
 * {@link MockWebApplicationContextLoader} must be annotated with this.
 *
 * @author afillatre@ippon.fr
 * @since 13/10/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MockWebApplication {
    /**
     * The location of the webapp directory relative to your project.
     * For maven users, this is generally src/main/webapp (default).
     */
    String webapp() default "src/main/webapp";

    /**
     * The portlet name
     */
    String name() default "";
}
