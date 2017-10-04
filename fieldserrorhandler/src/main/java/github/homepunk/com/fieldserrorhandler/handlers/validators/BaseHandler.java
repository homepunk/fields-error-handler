package github.homepunk.com.fieldserrorhandler.handlers.validators;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.Handler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.ParticularHandler;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

/**
 * Created by Homepunk on 22.09.2017.
 **/

public abstract class BaseHandler implements Handler {
    protected WeakReference<Context> contextWeakRef;
    protected int targetType;
    protected List<Integer> codes;
    protected Map<Integer, String> codeMessages;
    protected Map<Integer, Integer> codeResMessages;
    protected Map<Integer, ParticularHandler> particularValidators;

    public BaseHandler(Context context, int targetType) {
        this.targetType = targetType;
        this.contextWeakRef = new WeakReference<>(context);
        codes = new ArrayList<>();
        codeMessages = new HashMap<>();
        codeResMessages = new HashMap<>();
        particularValidators = new HashMap<>();
        initParticularHandlers();
    }

    protected abstract void initParticularHandlers();

    @Override
    public HandleResult handle(String target) {
        HandleResult handleResult = new HandleResult();
        handleResult.setTargetType(targetType);

        for (Integer code : codes) {
            ParticularHandler particularHandler = particularValidators.get(code);
            if (particularHandler.isSuccess(target)) {
                handleResult.setSuccess(true);
                handleResult.setMessage("");
            } else {
                String message = getMessage(code);
                handleResult.setMessage(message != null ? message : "");
                handleResult.setSuccess(false);
                break;
            }
        }

        return handleResult;
    }

    private String getMessage(Integer code) {
        String message = null;
        if (!codeResMessages.isEmpty()) {
            Integer messageResId = codeResMessages.get(code);
            if (messageResId != null) {
                message = contextWeakRef.get().getString(messageResId);
            }
        } else {
            contextWeakRef.clear();
        }

        if (message == null) {
            message = codeMessages.get(code);
        }

        return message;
    }

    @Override
    public void addHandler(TargetHandler handler) {
        init(handler);
    }

    protected void init(TargetHandler handler) {
        for (Integer code : handler.getCodes()) {
            if (!codes.contains(code)) {
                codes.add(0, code);
            }
        }

        particularValidators.putAll(handler.getValidators());
        codeMessages.putAll(handler.getCodeMessages());
        codeResMessages.putAll(handler.getCodeResMessages());
    }
}
