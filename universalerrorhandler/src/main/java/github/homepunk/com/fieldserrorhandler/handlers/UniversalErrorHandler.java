package github.homepunk.com.fieldserrorhandler.handlers;


import com.homepunk.github.models.TargetType;

import github.homepunk.com.fieldserrorhandler.handlers.builders.FieldHandler;
import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.interfaces.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleErrorResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleSuccessResultListener;

/**
 * Main interface which provides methods for error handling
 * <p>
 * Created by homepunk on 25.08.17.
 */

public interface UniversalErrorHandler {

    void setFieldValidator(@TargetType int fieldType, TargetHandler validator);

    void addTargetFieldHandler(int targetType, FieldHandler handler);

    /**
     * Either validate text or handleOnAction network error requests
     *
     * @param target     plain text to handleOnAction
     * @param targetType TargetType.NAME, TargetType.EMAIL, TargetType.NAME.PASSWORD,
     *                   TargetType.PHONE, TargetType.CUSTOM, TargetType.CREDIT_CARD
     */
    void handle(@TargetType int targetType, String target);

    /**
     * Plain text validation
     *
     * @param target    plain text to validate
     * @param filedType TargetType.NAME, TargetType.EMAIL, TargetType.NAME.PASSWORD,
     *                  TargetType.PHONE, TargetType.CUSTOM, TargetType.CREDIT_CARD
     * @return result of validating @bindTarget
     */
    boolean isValid(@TargetType int filedType, String target);

    /**
     * onFieldHandleResult method would be called every time when validation on handleOnAction fails
     */
    void setOnHandleFailListener(HandleErrorResultListener handleErrorResultListener);

    /**
     * Sets general @action to be executed on success handling
     *
     * @param handleSuccessResultListener action to be executed
     */
    void setOnHandleSuccessListener(HandleSuccessResultListener handleSuccessResultListener);

    void setOnHandleResultListener(HandleResultListener handleResultListener);
}
