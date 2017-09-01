package github.homepunk.com.universalerrorhandler.handlers.requests;


import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.universalerrorhandler.handlers.interfaces.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.SuccessResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.UniversalErrorHandler;

/**
 * Created by homepunk on 25.08.17.
 */

public class RequestsErrorHandler implements UniversalErrorHandler {
    @Override
    public void handle(@UniversalFieldType String targetType, String target) {

    }

    @Override
    public boolean isValid(@UniversalFieldType String errorType, String target) {
        return false;
    }


    @Override
    public void setOnHandleFailListener(ErrorResultListener errorResultListener) {

    }

    @Override
    public void setOnHandleSuccessListener(SuccessResultListener successResultListener) {

    }
}
