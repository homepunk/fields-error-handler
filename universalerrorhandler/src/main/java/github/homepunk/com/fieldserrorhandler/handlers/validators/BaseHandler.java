package github.homepunk.com.fieldserrorhandler.handlers.validators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.Handler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.ParticularHandler;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

/**
 * Created by Homepunk on 22.09.2017.
 **/

public abstract class BaseHandler implements Handler {
    protected int targetType;
    protected Set<Integer> codes;
    protected Map<Integer, String> codeMessages;
    protected Map<Integer, Integer> codeResMessages;
    protected Map<Integer, ParticularHandler> particularValidators;

    public BaseHandler(int targetType) {
        this.targetType = targetType;
        codes = new HashSet<>();
        codeMessages = new HashMap<>();
        codeResMessages = new HashMap<>();
        particularValidators = new HashMap<>();
    }

    protected abstract void initParticularHandlers();

    @Override
    public HandleResult handle(String target) {
        initParticularHandlers();
        HandleResult handleResult = new HandleResult();
        handleResult.setTargetType(targetType);

        for (Integer code : codes) {
            ParticularHandler particularHandler = particularValidators.get(code);
            if (particularHandler.isSuccess(target)) {
                handleResult.setSuccess(true);
                handleResult.setMessage("");
            } else {
                handleResult.setSuccess(false);
                String message = codeMessages.get(code);

                if (message != null) {
                    handleResult.setMessage(message);
                }
                break;
            }
        }

        return handleResult;
    }

    @Override
    public void addHandler(TargetHandler handler) {
        init(handler);
    }

    protected void init(TargetHandler handler) {
        codes.addAll(handler.getCodes());
        particularValidators.putAll(handler.getValidators());
        codeMessages.putAll(handler.getCodeMessages());
        codeResMessages.putAll(handler.getCodeResMessages());
    }
}
