package github.homepunk.com.universalerrorhandler.handlers.interfaces.listeners;


import github.homepunk.com.universalerrorhandler.models.UniversalTargetType;

/**
 * Created by homepunk on 24.08.17.
 */

public interface HandleFailListener {
    void onHandleFailed(@UniversalTargetType int errorType);
}
