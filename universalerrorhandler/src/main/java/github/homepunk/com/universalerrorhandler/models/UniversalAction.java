package github.homepunk.com.universalerrorhandler.models;

import android.support.annotation.IntDef;

import static github.homepunk.com.universalerrorhandler.models.UniversalAction.AFTER_TEXT_CHANGE;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.BEFORE_TEXT_CHANGE;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.NONE;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_CLICK;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_FOCUS_MISS;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_TEXT_CHANGE;

/**
 * Created by homepunk on 25.08.17.
 */

@IntDef({NONE, ON_CLICK, ON_FOCUS_MISS, ON_TEXT_CHANGE, AFTER_TEXT_CHANGE, BEFORE_TEXT_CHANGE})
public @interface UniversalAction {
    int NONE = -1;
    int ON_CLICK = 1;
    int ON_FOCUS_MISS = 2;
    int ON_TEXT_CHANGE = 3;
    int AFTER_TEXT_CHANGE = 4;
    int BEFORE_TEXT_CHANGE = 5;
}
