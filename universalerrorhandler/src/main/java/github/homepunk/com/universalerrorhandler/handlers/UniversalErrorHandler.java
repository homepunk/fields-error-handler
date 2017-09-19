package github.homepunk.com.universalerrorhandler.handlers;


import com.homepunk.github.models.UniversalFieldAction;
import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;
import github.homepunk.com.universalerrorhandler.handlers.listeners.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.listeners.FieldsHandleListener;
import github.homepunk.com.universalerrorhandler.handlers.listeners.SuccessResultListener;

/**
 * Main interface which provides methods for error handling
 * <p>
 * Created by homepunk on 25.08.17.
 */

public interface UniversalErrorHandler {

    void setFieldValidator(@UniversalFieldType int fieldType, UniversalValidator validator);

    /**
     * Either validate text or handleOnAction network error requests
     *
     * @param target     plain text to handleOnAction
     * @param targetType UniversalFieldType.NAME, UniversalFieldType.EMAIL, UniversalFieldType.NAME.PASSWORD,
     *                   UniversalFieldType.PHONE, UniversalFieldType.CUSTOM, UniversalFieldType.CREDIT_CARD
     */
    void handle(@UniversalFieldType int targetType, String target);

    /**
     * Plain text validation
     *
     * @param target    plain text to validate
     * @param filedType UniversalFieldType.NAME, UniversalFieldType.EMAIL, UniversalFieldType.NAME.PASSWORD,
     *                  UniversalFieldType.PHONE, UniversalFieldType.CUSTOM, UniversalFieldType.CREDIT_CARD
     * @return result of validating @target
     */
    boolean isValid(@UniversalFieldType int filedType, String target);

    /**
     * onFieldHandleResult method would be called every time when validation on handleOnAction fails
     */
    void setOnHandleFailListener(ErrorResultListener errorResultListener);

    /**
     * Sets general @action to be executed on success handling
     *
     * @param successResultListener action to be executed
     */
    void setOnHandleSuccessListener(SuccessResultListener successResultListener);

    void setOnHandleResultListener(FieldsHandleListener handleResultListener);
}
