package github.homepunk.com.universalerrorhandler.handlers;

import android.util.Patterns;

import github.homepunk.com.universalerrorhandler.handlers.interfaces.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.listeners.HandleFailListener;
import github.homepunk.com.universalerrorhandler.handlers.validators.CreditCardValidator;
import github.homepunk.com.universalerrorhandler.handlers.validators.PhoneValidator;
import github.homepunk.com.universalerrorhandler.models.UniversalTargetType;
import io.reactivex.functions.Action;

import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.CREDIT_CARD;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.EMAIL;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.NAME;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.PASSWORD;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.PHONE;

/**
 * Created by homepunk on 24.08.17.
 */

public class FieldsErrorHandler implements UniversalErrorHandler {
    private static UniversalErrorHandler instance;

    private Action onHandleSuccessAction;
    private HandleFailListener failListener;

    private FieldsErrorHandler() {}

    public static UniversalErrorHandler getInstance() {
        if (instance == null) {
            instance = new FieldsErrorHandler();
        }

        return instance;
    }
    @Override
    public void handle(@UniversalTargetType int targetType, String target) {
        switch (targetType) {
            case NAME: {
                handleError(targetType, isNameValid(target));
                break;
            }
            case EMAIL: {
                handleError(targetType, isEmailValid(target));
                break;
            }
            case PHONE: {
                handleError(targetType, PhoneValidator.isValid(target));
            }
            case PASSWORD: {
                handleError(targetType, isPasswordValid(target));
                break;
            }
            case CREDIT_CARD: {
                handleError(targetType, CreditCardValidator.isValid(target));
            }
            default: {
                runOnHandleSuccessAction();
            }
        }
    }

    private void handleError(@UniversalTargetType int errorType, boolean isValid) {
        if (!isValid) {
            failListener.onHandleFailed(errorType);
        } else {
            runOnHandleSuccessAction();
        }
    }

    @Override
    public boolean isValid(@UniversalTargetType int errorType, String target) {
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
    public void doOnHandleSuccess(Action action) {
        this.onHandleSuccessAction = action;
    }

    @Override
    public void setOnHandleFailListener(HandleFailListener handleFailListener) {
        this.failListener = handleFailListener;
    }

    private void runOnHandleSuccessAction() {
        if (onHandleSuccessAction != null) {
            try {
                onHandleSuccessAction.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
