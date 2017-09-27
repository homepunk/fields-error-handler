package github.homepunk.com.fieldserrorhandler.handlers.validators;

import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.ParticularHandler;

import static com.homepunk.github.models.TargetErrorType.TEXT_CANT_BE_EMPTY;
import static com.homepunk.github.models.TargetErrorType.TEXT_CONTAINS_SPACES;
import static com.homepunk.github.models.TargetType.PASSWORD;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class PasswordHandler extends BaseHandler {
    public PasswordHandler() {
        super(PASSWORD);
    }

    @Override
    protected void initParticularHandlers() {
        TargetHandler passwordHandler = TargetHandler.getBuilder()
                .add(TEXT_CONTAINS_SPACES, "Password can't contain spaces", new ParticularHandler() {
                    @Override
                    public boolean isSuccess(String target) {
                        return !target.contains(" ");
                    }
                })
                .add(TEXT_CANT_BE_EMPTY, "Password can't be empty", new ParticularHandler() {
                            @Override
                            public boolean isSuccess(String target) {
                                return target.length() > 0;
                            }
                        }
                )
                .build();
        init(passwordHandler);
    }
}
