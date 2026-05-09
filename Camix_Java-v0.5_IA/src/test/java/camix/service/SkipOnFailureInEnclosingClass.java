package camix.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marque une classe {@code @Nested} a ignorer si une classe englobante a deja echoue.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipOnFailureInEnclosingClass
{
}
