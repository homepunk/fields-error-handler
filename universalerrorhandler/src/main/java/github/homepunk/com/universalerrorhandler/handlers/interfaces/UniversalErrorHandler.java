package github.homepunk.com.universalerrorhandler.handlers.interfaces;


import com.homepunk.github.models.UniversalFieldType;

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
     * @param targetType UniversalFieldType.NAME, UniversalFieldType.EMAIL, UniversalFieldType.NAME.PASSWORD,
     *                   UniversalFieldType.PHONE, UniversalFieldType.CUSTOM, UniversalFieldType.CREDIT_CARD
     */
    void handle(@UniversalFieldType String targetType, String target);

    /**
     * Plain text validation
     *
     * @param target    plain text to validate
     * @param errorType UniversalFieldType.NAME, UniversalFieldType.EMAIL, UniversalFieldType.NAME.PASSWORD,
     *                  UniversalFieldType.PHONE, UniversalFieldType.CUSTOM, UniversalFieldType.CREDIT_CARD
     * @return result of validating @target
     */
    boolean isValid(@UniversalFieldType String errorType, String target);

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
}
