package github.homepunk.com.fieldserrorhandler.handlers.fields.validators;

import android.util.Patterns;

import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.interfaces.TargetValidator;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

import static com.homepunk.github.models.TargetType.PASSWORD;

/**
 * Created by homepunk on 26.08.17.
 */

public class PhoneValidator implements TargetValidator {
    @Override
    public HandleResult isValid(String phone) {
        HandleResult handleResult = new HandleResult();
        handleResult.setTargetType(PASSWORD);
        handleResult.setSuccess(true);
        if (phone.length() > 0) {
            if (phone.length() > 2 && Patterns.PHONE.matcher(phone).matches()) {
                handleResult.setSuccess(false);
                handleResult.setMessage("Phone isn't valid");
            }
        } else {
            handleResult.setSuccess(false);
            handleResult.setMessage("Phone can't be empty");
        }

        return handleResult;
    }
}
