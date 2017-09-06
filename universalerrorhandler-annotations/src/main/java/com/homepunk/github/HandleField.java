package com.homepunk.github;

import com.homepunk.github.models.UniversalFieldAction;
import com.homepunk.github.models.UniversalFieldType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Target(FIELD)
@Retention(SOURCE)
public @interface HandleField  {
    @UniversalFieldType int value();
    @UniversalFieldAction int action() default 3;
}
