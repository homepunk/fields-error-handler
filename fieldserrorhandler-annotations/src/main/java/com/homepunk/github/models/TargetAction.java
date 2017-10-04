package com.homepunk.github.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.homepunk.github.models.TargetAction.AFTER_TEXT_CHANGE;
import static com.homepunk.github.models.TargetAction.BEFORE_TEXT_CHANGE;
import static com.homepunk.github.models.TargetAction.NO_ACTION;
import static com.homepunk.github.models.TargetAction.ON_CLICK;
import static com.homepunk.github.models.TargetAction.ON_FOCUS;
import static com.homepunk.github.models.TargetAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.TargetAction.ON_TEXT_CHANGE;

/**
 * Created by homepunk on 25.08.17.
 */

@IntDef({NO_ACTION, ON_CLICK, ON_FOCUS, ON_FOCUS_MISS, ON_TEXT_CHANGE, AFTER_TEXT_CHANGE, BEFORE_TEXT_CHANGE})
@Retention(RetentionPolicy.SOURCE)
public @interface TargetAction {
    int NO_ACTION = -1;
    int ON_CLICK = 1;
    int ON_FOCUS = 2;
    int ON_FOCUS_MISS = 3;
    int ON_TEXT_CHANGE = 4;
    int AFTER_TEXT_CHANGE = 5;
    int BEFORE_TEXT_CHANGE = 6;
}
