package github.homepunk.com.universalerrorhandler.handlers.listeners;


import com.homepunk.github.models.UniversalFieldType;

/**
 * Created by homepunk on 27.08.17.
 */

public interface FieldsHandleListener {
    void onFieldHandleResult(@UniversalFieldType int targetType, boolean isSuccess);
}
