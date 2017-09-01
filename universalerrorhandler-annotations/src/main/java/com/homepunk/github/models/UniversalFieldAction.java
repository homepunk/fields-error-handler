package com.homepunk.github.models;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.homepunk.github.models.UniversalFieldAction.AFTER_TEXT_CHANGE;
import static com.homepunk.github.models.UniversalFieldAction.BEFORE_TEXT_CHANGE;
import static com.homepunk.github.models.UniversalFieldAction.NONE;
import static com.homepunk.github.models.UniversalFieldAction.ON_CLICK;
import static com.homepunk.github.models.UniversalFieldAction.ON_FOCUS;
import static com.homepunk.github.models.UniversalFieldAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.UniversalFieldAction.ON_TEXT_CHANGE;

/**
 * Created by homepunk on 25.08.17.
 */

@StringDef({NONE, ON_CLICK, ON_FOCUS, ON_FOCUS_MISS, ON_TEXT_CHANGE, AFTER_TEXT_CHANGE, BEFORE_TEXT_CHANGE})
@Retention(RetentionPolicy.SOURCE)
public @interface UniversalFieldAction {
    String NONE = "None";
    String ON_CLICK = "On_Click";
    String ON_FOCUS = "On_Focus";
    String ON_FOCUS_MISS = "On_Focus_Miss";
    String ON_TEXT_CHANGE = "On_Text_Change";
    String AFTER_TEXT_CHANGE = "After_Text_Change";
    String BEFORE_TEXT_CHANGE = "Before_Text_Change";
}
