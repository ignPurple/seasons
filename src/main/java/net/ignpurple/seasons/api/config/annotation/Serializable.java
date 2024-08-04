package net.ignpurple.seasons.api.config.annotation;

import net.ignpurple.seasons.api.config.serializer.Serializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Serializable {
    Class<? extends Serializer<?>> value();
}
