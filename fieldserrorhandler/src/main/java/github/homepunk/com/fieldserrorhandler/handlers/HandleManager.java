package github.homepunk.com.fieldserrorhandler.handlers;

import android.content.Context;
import android.util.SparseArray;

import com.homepunk.github.models.TargetType;

import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleErrorResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleSuccessResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.validators.CardHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.CustomTargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.EmailHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.NameHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.PasswordHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.PhoneHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.Handler;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

import static com.homepunk.github.models.TargetType.CREDIT_CARD;
import static com.homepunk.github.models.TargetType.EMAIL;
import static com.homepunk.github.models.TargetType.NAME;
import static com.homepunk.github.models.TargetType.PASSWORD;
import static com.homepunk.github.models.TargetType.PHONE;

/**
 * Created by homepunk on 24.08.17.
 */

public class HandleManager {
    private final Context context;
    private SparseArray<Handler> handlers;
    private HandleResultListener resultListener;
    private HandleErrorResultListener failListener;
    private HandleSuccessResultListener successListener;

    public HandleManager(Context context) {
        this.context = context;
        handlers = new SparseArray<>();
        handlers.put(NAME, new NameHandler(context));
        handlers.put(EMAIL, new EmailHandler(context));
        handlers.put(PHONE, new PhoneHandler(context));
        handlers.put(PASSWORD, new PasswordHandler(context));
        handlers.put(CREDIT_CARD, new CardHandler());
    }

    public void setFieldValidator(@TargetType int targetType, Handler handler) {
        handlers.put(targetType, handler);
    }

    public void addTargetFieldHandler(int targetType, TargetHandler handler) {
        if (targetType != NAME && targetType != EMAIL && targetType != PHONE && targetType != PASSWORD && targetType != CREDIT_CARD) {
            handlers.put(targetType, new CustomTargetHandler(context, targetType));
        }

        handlers.get(targetType).addHandler(handler);
    }

    /**
     * Either validate text or handleOnAction network error requests
     *
     * @param target     plain text to handleOnAction
     * @param targetType TargetType.NAME, TargetType.EMAIL, TargetType.NAME.PASSWORD,
     *                   TargetType.PHONE, TargetType.CUSTOM, TargetType.CREDIT_CARD
     */
    public void handle(@TargetType int targetType, String target) {
        Handler validator = handlers.get(targetType);
        handleError(validator.handle(target));
    }

    /**
     * Plain text validation
     *
     * @param target    plain text to validate
     * @param filedType TargetType.NAME, TargetType.EMAIL, TargetType.NAME.PASSWORD,
     *                  TargetType.PHONE, TargetType.CUSTOM, TargetType.CREDIT_CARD
     * @return result of validating @bindTarget
     */
    public boolean isValid(@TargetType int filedType, String target) {
        return handlers.get(filedType).handle(target).isSuccess();
    }

    /**
     * onFieldHandleResult method would be called every time when validation on handleOnAction fails
     */
    public void setOnHandleFailListener(HandleErrorResultListener handleErrorResultListener) {
        this.failListener = handleErrorResultListener;
    }

    /**
     * Sets general @action to be executed on success handling
     *
     * @param handleSuccessResultListener action to be executed
     */
    public void setOnHandleSuccessListener(HandleSuccessResultListener handleSuccessResultListener) {
        this.successListener = handleSuccessResultListener;
    }

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
