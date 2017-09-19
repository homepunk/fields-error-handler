package github.homepunk.com.universalerrorhandler.handlers.listeners;


import com.homepunk.github.models.UniversalFieldType;

/**
 * Created by homepunk on 24.08.17.
 */

public interface ErrorResultListener {
    void onErrorResult(@UniversalFieldType int targetType);
}
