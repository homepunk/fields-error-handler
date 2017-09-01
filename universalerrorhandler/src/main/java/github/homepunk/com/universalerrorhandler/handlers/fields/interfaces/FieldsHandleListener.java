package github.homepunk.com.universalerrorhandler.handlers.fields.interfaces;


import com.homepunk.github.models.UniversalFieldType;

/**
 * Created by homepunk on 27.08.17.
 */

public interface FieldsHandleListener {
    void onFieldHandleResult(@UniversalFieldType String targetType, boolean isSuccess, String error);
}
