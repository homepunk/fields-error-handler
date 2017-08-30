package github.homepunk.com.universalerrorhandler.handlers.fields;

import android.util.Patterns;

import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.universalerrorhandler.handlers.fields.validators.CreditCardValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.PhoneValidator;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.SuccessResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.UniversalErrorHandler;

import static com.homepunk.github.models.UniversalFieldType.*;
import static github.homepunk.com.universalerrorhandler.Constants.EMPTY_EMAIL;
import static github.homepunk.com.universalerrorhandler.Constants.EMPTY_PASSWORD;

/**
 * Created by homepunk on 24.08.17.
 */

public class FieldsErrorHandler implements UniversalErrorHandler {
    private static UniversalErrorHandler instance;

    private ErrorResultListener currentFailListener;
    private SuccessResultListener currentSuccessListener;

    private FieldsErrorHandler() {
    }

    public static UniversalErrorHandler getInstance() {
        if (instance == null) {
            instance = new FieldsErrorHandler();
        }

        return instance;
    }

    @Override
    public void handle(@UniversalFieldType int targetType, String target) {
        final String errorMessage = null;

        switch (targetType) {
            case NAME: {
                handleError(targetType, isNameValid(target), errorMessage);
                break;
            }
            case EMAIL: {
                handleError(targetType, isEmailValid(target), EMPTY_EMAIL);
                break;
            }
            case PHONE: {
                handleError(targetType, PhoneValidator.isValid(target), errorMessage);
            }
            case PASSWORD: {
                handleError(targetType, isPasswordValid(target), EMPTY_PASSWORD);
                break;
            }
            case CREDIT_CARD: {
                handleError(targetType, CreditCardValidator.isValid(target), errorMessage);
            }
            default: {
                if (currentSuccessListener != null) {
                    currentSuccessListener.onSuccess();
                }
            }
        }
    }

    private void handleError(@UniversalFieldType int targetType, boolean isValid, String errorMessage) {
        if (!isValid) {
            if (currentFailListener != null) {
                currentFailListener.onErrorResult(errorMessage);
            }
        } else {
            if (currentSuccessListener != null) {
                currentSuccessListener.onSuccess();
            }
        }
    }

    @Override
    public boolean isValid(@UniversalFieldType int errorType, String target) {
        switch (errorType) {
            case NAME: {
                return isNameValid(target);
            }
            case EMAIL: {
                return isEmailValid(target);
            }
            case PASSWORD: {
                return isPasswordValid(target);
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public void setOnHandleFailListener(ErrorResultListener errorResultListener) {
        this.currentFailListener = errorResultListener;
    }

    @Override
    public void setOnHandleSuccessListener(SuccessResultListener successResultListener) {
        this.currentSuccessListener = successResultListener;
    }

    private boolean isNameValid(String name) {
        return name.length() > 1;
    }

    private boolean isEmailValid(String email) {
        return email.length() > 6 && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() > 2 && Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }
}
