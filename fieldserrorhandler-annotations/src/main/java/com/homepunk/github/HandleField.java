package com.homepunk.github;

import com.homepunk.github.models.TargetAction;
import com.homepunk.github.models.TargetType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Target(FIELD)
@Retention(SOURCE)
public @interface HandleField  {
    @TargetType int value();
    @TargetAction int action() default 3;
}
