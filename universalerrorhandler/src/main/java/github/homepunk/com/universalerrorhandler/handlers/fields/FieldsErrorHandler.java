package github.homepunk.com.universalerrorhandler.handlers.fields;

import android.util.Patterns;

import github.homepunk.com.universalerrorhandler.handlers.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.listeneres.HandleFailListener;
import github.homepunk.com.universalerrorhandler.handlers.fields.listeneres.HandleSuccessListener;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.CreditCardValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.PhoneValidator;
import github.homepunk.com.universalerrorhandler.models.UniversalFieldType;

import static github.homepunk.com.universalerrorhandler.models.Constants.EMPTY_EMAIL;
import static github.homepunk.com.universalerrorhandler.models.Constants.EMPTY_PASSWORD;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.CREDIT_CARD;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.EMAIL;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.NAME;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.PASSWORD;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.PHONE;

/**
 * Created by homepunk on 24.08.17.
 */

public class FieldsErrorHandler implements UniversalErrorHandler {
    private static UniversalErrorHandler instance;

    private HandleFailListener currentFailListener;
    private HandleSuccessListener currentSuccessListener;

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
                    currentSuccessListener.onHandleSuccess();
                }
            }
        }
    }

    private void handleError(@UniversalFieldType int targetType, boolean isValid, String errorMessage) {
        if (!isValid) {
            if (currentFailListener != null) {
                currentFailListener.onHandleFailed(errorMessage);
            }
        } else {
            if (currentSuccessListener != null) {
                currentSuccessListener.onHandleSuccess();
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
    public void setOnHandleFailListener(HandleFailListener handleFailListener) {
        this.currentFailListener = handleFailListener;
    }

    @Override
    public void setOnHandleSuccessListener(HandleSuccessListener handleSuccessListener) {
        this.currentSuccessListener = handleSuccessListener;
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
