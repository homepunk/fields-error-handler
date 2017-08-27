package github.homepunk.com.universalerrorhandler.annotations;


import github.homepunk.com.universalerrorhandler.models.UniversalFieldType;

/**
 * Created by homepunk on 25.08.17.
 */

public @interface HandleField {
    @UniversalFieldType int value();
}
