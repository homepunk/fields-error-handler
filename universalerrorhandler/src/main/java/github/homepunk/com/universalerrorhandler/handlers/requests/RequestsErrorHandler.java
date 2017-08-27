package github.homepunk.com.universalerrorhandler.handlers.requests;


import github.homepunk.com.universalerrorhandler.handlers.interfaces.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.SuccessResultListener;
import github.homepunk.com.universalerrorhandler.models.UniversalFieldType;

/**
 * Created by homepunk on 25.08.17.
 */

public class RequestsErrorHandler implements UniversalErrorHandler {
    @Override
    public void handle(@UniversalFieldType int targetType, String target) {

    }

    @Override
    public boolean isValid(@UniversalFieldType int errorType, String target) {
        return false;
    }


    @Override
    public void setOnHandleFailListener(ErrorResultListener errorResultListener) {

    }

    @Override
    public void setOnHandleSuccessListener(SuccessResultListener successResultListener) {

    }
}
