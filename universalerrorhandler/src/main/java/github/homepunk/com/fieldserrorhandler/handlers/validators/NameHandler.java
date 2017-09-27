package github.homepunk.com.fieldserrorhandler.handlers.validators;

import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.ParticularHandler;

import static com.homepunk.github.models.TargetErrorType.TEXT_CANT_BE_EMPTY;
import static com.homepunk.github.models.TargetType.NAME;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class NameHandler extends BaseHandler {
    public NameHandler() {
        super(NAME);
    }

    @Override
    protected void initParticularHandlers() {
        init(TargetHandler.getBuilder().add(TEXT_CANT_BE_EMPTY, "Name can't be empty", new ParticularHandler() {
            @Override
            public boolean isSuccess(String target) {
                return target.length() > 1;
            }
        }).build());
    }
}
