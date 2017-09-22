package github.homepunk.com.fieldserrorhandler.handlers.fields;

import android.util.SparseArray;

import com.homepunk.github.models.TargetType;

import github.homepunk.com.fieldserrorhandler.handlers.UniversalErrorHandler;
import github.homepunk.com.fieldserrorhandler.handlers.builders.FieldHandler;
import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.CreditCardHandler;
import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.EmailHandler;
import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.NameHandler;
import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.PasswordHandler;
import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.PhoneHandler;
import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.interfaces.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleErrorResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleSuccessResultListener;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

import static com.homepunk.github.models.TargetType.CREDIT_CARD;
import static com.homepunk.github.models.TargetType.EMAIL;
import static com.homepunk.github.models.TargetType.NAME;
import static com.homepunk.github.models.TargetType.PASSWORD;
import static com.homepunk.github.models.TargetType.PASSWORD_CONFIRMATION;
import static com.homepunk.github.models.TargetType.PHONE;

/**
 * Created by homepunk on 24.08.17.
 */

public class FieldsErrorHandler implements UniversalErrorHandler {
    private HandleResultListener resultListener;
    private HandleErrorResultListener failListener;
    private HandleSuccessResultListener successListener;
    private SparseArray<TargetHandler> validatorsMap;

    public FieldsErrorHandler() {
        validatorsMap = new SparseArray<>();
        validatorsMap.put(NAME, new NameHandler());
        validatorsMap.put(EMAIL, new EmailHandler());
        validatorsMap.put(PHONE, new PhoneHandler());
        validatorsMap.put(PASSWORD, new PasswordHandler());
        validatorsMap.put(PASSWORD_CONFIRMATION, new PasswordHandler());
        validatorsMap.put(CREDIT_CARD, new CreditCardHandler());
    }

    @Override
    public void setFieldValidator(@TargetType int fieldType, TargetHandler validator) {
        validatorsMap.put(fieldType, validator);
    }

    @Override
    public void addTargetFieldHandler(int targetType, FieldHandler handler) {
        validatorsMap.get(targetType).addHandler(handler);
    }

    @Override
    public void handle(@TargetType int targetType, String target) {
        TargetHandler validator = validatorsMap.get(targetType);
        handleError(validator.handle(target));
    }

    @Override
    public boolean isValid(@TargetType int filedType, String target) {
        return validatorsMap.get(filedType).handle(target).isSuccess();
    }

    @Override
    public void setOnHandleFailListener(HandleErrorResultListener handleErrorResultListener) {
        this.failListener = handleErrorResultListener;
    }

    @Override
    public void setOnHandleSuccessListener(HandleSuccessResultListener handleSuccessResultListener) {
        this.successListener = handleSuccessResultListener;
    }

    @Override
    public void setOnHandleResultListener(HandleResultListener resultListener) {
        this.resultListener = resultListener;
    }

    private void handleError(HandleResult handleResult) {
        if (resultListener != null) {
            resultListener.onFieldHandleResult(handleResult);
        }

        if (!handleResult.isSuccess()) {
            if (failListener != null) {
                failListener.onErrorResult(handleResult.getTargetType(), handleResult.getMessage());
            }
        } else {
            if (successListener != null) {
                successListener.onSuccess(handleResult.getTargetType());
            }
        }
    }
}
