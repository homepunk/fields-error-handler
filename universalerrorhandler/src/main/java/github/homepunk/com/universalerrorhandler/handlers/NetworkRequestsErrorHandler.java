package github.homepunk.com.universalerrorhandler.handlers;


import github.homepunk.com.universalerrorhandler.handlers.interfaces.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.listeners.HandleFailListener;
import github.homepunk.com.universalerrorhandler.models.UniversalTargetType;
import io.reactivex.functions.Action;

/**
 * Created by homepunk on 25.08.17.
 */

public class NetworkRequestsErrorHandler implements UniversalErrorHandler {
    @Override
    public void handle(@UniversalTargetType int targetType, String target) {

    }

    @Override
    public boolean isValid(@UniversalTargetType int errorType, String target) {
        return false;
    }

    @Override
    public void doOnHandleSuccess(Action action) {

    }

    @Override
    public void setOnHandleFailListener(HandleFailListener handleFailListener) {

    }
}
