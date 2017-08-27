package github.homepunk.com.universalerrorhandler.annotations;


import github.homepunk.com.universalerrorhandler.models.UniversalTargetType;

/**
 * Created by homepunk on 25.08.17.
 */

public @interface HandleField {
    @UniversalTargetType int value();
}
