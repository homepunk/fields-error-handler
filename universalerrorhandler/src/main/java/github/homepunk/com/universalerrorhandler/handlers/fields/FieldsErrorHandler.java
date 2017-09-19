package github.homepunk.com.universalerrorhandler.handlers.fields;

import android.util.SparseArray;

import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.universalerrorhandler.handlers.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.CreditCardValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.EmailValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.NameValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.PasswordValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.PhoneValidator;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;
import github.homepunk.com.universalerrorhandler.handlers.listeners.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.listeners.FieldsHandleListener;
import github.homepunk.com.universalerrorhandler.handlers.listeners.SuccessResultListener;

import static com.homepunk.github.models.UniversalFieldType.CREDIT_CARD;
import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.NAME;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD_CONFIRMATION;
import static com.homepunk.github.models.UniversalFieldType.PHONE;

/**
 * Created by homepunk on 24.08.17.
 */

public class FieldsErrorHandler implements UniversalErrorHandler {
    private static UniversalErrorHandler instance;

    private FieldsHandleListener resultListener;
    private ErrorResultListener failListener;
    private SuccessResultListener successListener;
    private SparseArray<UniversalValidator> validatorsMap;

    public FieldsErrorHandler() {
        validatorsMap = new SparseArray<>();
        validatorsMap.put(NAME, new NameValidator());
        validatorsMap.put(EMAIL, new EmailValidator());
        validatorsMap.put(PHONE, new PhoneValidator());
        validatorsMap.put(PASSWORD, new PasswordValidator());
        validatorsMap.put(PASSWORD_CONFIRMATION, new PasswordValidator());
        validatorsMap.put(CREDIT_CARD, new CreditCardValidator());
    }

    @Override
    public void setFieldValidator(@UniversalFieldType int fieldType, UniversalValidator validator) {
        validatorsMap.put(fieldType, validator);
    }

    @Override
    public void handle(@UniversalFieldType int targetType, String target) {
        UniversalValidator validator = validatorsMap.get(targetType);
        handleError(targetType, validator.isValid(target));
    }

    @Override
    public boolean isValid(@UniversalFieldType int filedType, String target) {
        return validatorsMap.get(filedType).isValid(target);
    }

    @Override
    public void setOnHandleFailListener(ErrorResultListener errorResultListener) {
        this.failListener = errorResultListener;
    }

    @Override
    public void setOnHandleSuccessListener(SuccessResultListener successResultListener) {
        this.successListener = successResultListener;
    }

    @Override
    public void setOnHandleResultListener(FieldsHandleListener resultListener) {
        this.resultListener = resultListener;
    }

    private void handleError(int targetType, boolean isValid) {
        if (resultListener != null) {
            resultListener.onFieldHandleResult(targetType, isValid);
        }

        if (!isValid) {
            if (failListener != null) {
                failListener.onErrorResult(targetType);
            }
        } else {
            if (successListener != null) {
                successListener.onSuccess(targetType);
            }
        }
    }
}
