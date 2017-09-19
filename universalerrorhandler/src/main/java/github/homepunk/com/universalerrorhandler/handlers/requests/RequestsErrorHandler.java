package github.homepunk.com.universalerrorhandler.handlers.requests;


import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;
import github.homepunk.com.universalerrorhandler.handlers.listeners.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.listeners.FieldsHandleListener;
import github.homepunk.com.universalerrorhandler.handlers.listeners.SuccessResultListener;
import github.homepunk.com.universalerrorhandler.handlers.UniversalErrorHandler;

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

    @Override
    public void setOnHandleResultListener(FieldsHandleListener handleResultListener) {

    }

    @Override
    public void setFieldValidator(@UniversalFieldType int fieldType, UniversalValidator validator) {

    }
}
