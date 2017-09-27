package github.homepunk.com.fieldserrorhandler.handlers.validators;

import android.util.Patterns;

import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.ParticularHandler;

import static com.homepunk.github.models.TargetErrorType.TEXT_IS_NOT_VALID;
import static com.homepunk.github.models.TargetType.PHONE;

/**
 * Created by homepunk on 26.08.17.
 */

public class PhoneHandler extends BaseHandler {
    public PhoneHandler() {
        super(PHONE);
    }

    @Override
    protected void initParticularHandlers() {
        init(TargetHandler.getBuilder()
                .add(TEXT_IS_NOT_VALID, "Phone not valid", new ParticularHandler() {
                    @Override
                    public boolean isSuccess(String target) {
                        String plainText = target.trim().replaceAll("[()]", "");

                        return Patterns.PHONE.matcher(plainText).matches();
                    }
                }).build());
    }
}
