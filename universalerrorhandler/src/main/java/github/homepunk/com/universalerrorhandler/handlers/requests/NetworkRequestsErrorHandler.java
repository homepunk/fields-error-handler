package github.homepunk.com.universalerrorhandler.handlers.requests;


import github.homepunk.com.universalerrorhandler.handlers.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.listeneres.HandleFailListener;
import github.homepunk.com.universalerrorhandler.handlers.fields.listeneres.HandleSuccessListener;
import github.homepunk.com.universalerrorhandler.models.UniversalFieldType;

/**
 * Created by homepunk on 25.08.17.
 */

public class NetworkRequestsErrorHandler implements UniversalErrorHandler {
    @Override
    public void handle(@UniversalFieldType int targetType, String target) {

    }

    @Override
    public boolean isValid(@UniversalFieldType int errorType, String target) {
        return false;
    }


    @Override
    public void setOnHandleFailListener(HandleFailListener handleFailListener) {

    }

    @Override
    public void setOnHandleSuccessListener(HandleSuccessListener handleSuccessListener) {

    }
}
