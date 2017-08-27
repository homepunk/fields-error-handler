package github.homepunk.com.universalerrorhandler.handlers.interfaces;


import github.homepunk.com.universalerrorhandler.handlers.interfaces.listeners.HandleFailListener;
import github.homepunk.com.universalerrorhandler.models.UniversalTargetType;
import io.reactivex.functions.Action;

/**
 * Main interface which provides methods for error handling
 * <p>
 * Created by homepunk on 25.08.17.
 */

public interface UniversalErrorHandler {
    /**
     * Either validate text or handleOnAction network error requests
     *
     * @param target     plain text to handleOnAction
     * @param targetType UniversalTargetType.NAME, UniversalTargetType.EMAIL, UniversalTargetType.NAME.PASSWORD,
     *                   UniversalTargetType.PHONE, UniversalTargetType.CUSTOM, UniversalTargetType.CREDIT_CARD
     */
    void handle(@UniversalTargetType int targetType, String target);

    /**
     * Plain text validation
     *
     * @param target    plain text to validate
     * @param errorType UniversalTargetType.NAME, UniversalTargetType.EMAIL, UniversalTargetType.NAME.PASSWORD,
     *                  UniversalTargetType.PHONE, UniversalTargetType.CUSTOM, UniversalTargetType.CREDIT_CARD
     * @return result of validating @target
     */
    boolean isValid(@UniversalTargetType int errorType, String target);

    /**
     * Sets general @action to be executed on success handling
     *
     * @param action action to be executed
     */
    void doOnHandleSuccess(Action action);

    /**
     * onHandleFailed method would be called every time when validation on handleOnAction fails
     */
    void setOnHandleFailListener(HandleFailListener handleFailListener);
}
