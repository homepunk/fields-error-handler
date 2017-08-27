package github.homepunk.com.universalerrorhandler.handlers.fields.listeneres;

import github.homepunk.com.universalerrorhandler.models.UniversalFieldType;

/**
 * Created by homepunk on 27.08.17.
 */

public interface FieldsHandleListener {
    void onFieldHandleResult(@UniversalFieldType int targetType, boolean isSuccess, String error);
}
