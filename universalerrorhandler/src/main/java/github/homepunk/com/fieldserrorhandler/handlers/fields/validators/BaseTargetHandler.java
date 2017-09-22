package github.homepunk.com.fieldserrorhandler.handlers.fields.validators;

import java.util.Map;
import java.util.Set;

import github.homepunk.com.fieldserrorhandler.handlers.builders.FieldHandler;
import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.interfaces.ParticularHandler;
import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.interfaces.TargetHandler;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

/**
 * Created by Homepunk on 22.09.2017.
 **/

public abstract class BaseTargetHandler implements TargetHandler {
    protected int targetType;
    protected Set<Integer> codes;
    protected Map<Integer, String> codeMessages;
    protected Map<Integer, Integer> codeResMessages;
    protected Map<Integer ,ParticularHandler> particularValidators;

    public BaseTargetHandler(int targetType) {
        this.targetType = targetType;
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
    public void addHandler(FieldHandler handler) {
        init(handler);
    }

    protected void init(FieldHandler handler) {
        codes.addAll(handler.getCodes());
        particularValidators.putAll(handler.getValidators());
        codeMessages.putAll(handler.getCodeMessages());
        codeResMessages.putAll(handler.getCodeResMessages());
    }
}
