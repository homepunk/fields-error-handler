package com.homepunk.github;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Homepunk on 05.09.2017.
 **/

@Target(METHOD)
@Retention(SOURCE)
public @interface OnFieldHandleResult {
    String value();
}
