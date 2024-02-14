package com.bobocode.basics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.bobocode.basics.Level.BASIC;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Exercise {
    String value();
    Level complexityLevel() default BASIC;
}
