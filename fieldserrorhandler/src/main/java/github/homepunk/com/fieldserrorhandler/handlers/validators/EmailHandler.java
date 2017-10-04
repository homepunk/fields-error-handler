package github.homepunk.com.fieldserrorhandler.handlers.validators;

import android.content.Context;
import android.util.Patterns;

import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.ParticularHandler;

import static com.homepunk.github.models.TargetErrorType.TEXT_CANT_BE_EMPTY;
import static com.homepunk.github.models.TargetErrorType.TEXT_IS_NOT_VALID;
import static com.homepunk.github.models.TargetType.EMAIL;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class EmailHandler extends BaseHandler {
    public EmailHandler(Context context) {
        super(context, EMAIL);
    }

    @Override
    protected void initParticularHandlers() {
        init(TargetHandler.getBuilder()
                .add(TEXT_CANT_BE_EMPTY, "Email can't be empty", new ParticularHandler() {
                    @Override
                    public boolean isSuccess(String target) {
                        return target.length() > 4;
                    }
                })
                .add(TEXT_IS_NOT_VALID, "Email isn't valid", new ParticularHandler() {
                    @Override
                    public boolean isSuccess(String target) {
                        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
                    }
                }).build());
    }
}
