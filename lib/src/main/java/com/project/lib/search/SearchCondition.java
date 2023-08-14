package com.project.lib.search;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface SearchCondition {

    String value() default "";

    Class<?> qualifiedBy() default void.class;

    SearchType condition() default SearchType.EQUAL;

    String table() default "";
}
