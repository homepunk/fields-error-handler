package github.homepunk.com.universalerrorhandler.handlers.fields;

import android.util.SparseArray;

import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.universalerrorhandler.handlers.fields.validators.CreditCardValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.EmailValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.NameValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.PasswordValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.PhoneValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.SuccessResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.UniversalErrorHandler;

import static com.homepunk.github.models.UniversalFieldType.CREDIT_CARD;
import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.NAME;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD;
import static com.homepunk.github.models.UniversalFieldType.PHONE;

/**
 * Created by homepunk on 24.08.17.
 */

public class FieldsErrorHandler implements UniversalErrorHandler {
    private static UniversalErrorHandler instance;

    private ErrorResultListener currentFailListener;
    private SuccessResultListener currentSuccessListener;
    private SparseArray<UniversalValidator> validatorsMap;

    private FieldsErrorHandler() {
        validatorsMap = new SparseArray<>();
        validatorsMap.put(NAME, new NameValidator());
        validatorsMap.put(EMAIL, new EmailValidator());
        validatorsMap.put(PHONE, new PhoneValidator());
        validatorsMap.put(PASSWORD, new PasswordValidator());
        validatorsMap.put(CREDIT_CARD, new CreditCardValidator());
    }

    public static UniversalErrorHandler getInstance() {
        if (instance == null) {
            instance = new FieldsErrorHandler();
        }

        return instance;
    }

    @Override
    public void setFieldValidator(@UniversalFieldType int fieldType, UniversalValidator validator) {
        validatorsMap.put(fieldType, validator);
    }

    @Override
    public void handle(@UniversalFieldType int targetType, String target) {
        UniversalValidator validator = validatorsMap.get(targetType);
        handleError(validator.isValid(target), "Not valid");
    }

    @Override
    public boolean isValid(@UniversalFieldType int filedType, String target) {
        return validatorsMap.get(filedType).isValid(target);
    }

    @Override
    public void setOnHandleFailListener(ErrorResultListener errorResultListener) {
        this.currentFailListener = errorResultListener;
    }

    @Override
    public void setOnHandleSuccessListener(SuccessResultListener successResultListener) {
        this.currentSuccessListener = successResultListener;
    }

    private void handleError(boolean isValid, String errorMessage) {
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
}
