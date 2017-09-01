package com.homepunk.github;

import com.homepunk.github.models.UniversalFieldAction;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Homepunk on 30.08.2017.
 **/

@Retention(SOURCE)
@Target(FIELD)
public @interface HandleOnAction {
    @UniversalFieldAction String value();
}
