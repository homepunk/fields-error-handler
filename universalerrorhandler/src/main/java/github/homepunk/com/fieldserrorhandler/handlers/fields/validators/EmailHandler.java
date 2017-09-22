package github.homepunk.com.fieldserrorhandler.handlers.fields.validators;

import android.util.Patterns;

import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.interfaces.TargetValidator;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

import static com.homepunk.github.models.TargetType.EMAIL;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class EmailValidator implements TargetValidator {
    @Override
    public HandleResult isValid(String target) {
        HandleResult handleResult = new HandleResult();
        handleResult.setSuccess(false);
        handleResult.setTargetType(EMAIL);

        if (target.length() > 0) {
            if (Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
                handleResult.setSuccess(true);
            } else {
                handleResult.setSuccess(false);
                handleResult.setMessage("Email isn't valid");
            } if (target.contains(" ")) {
                handleResult.setMessage("Email can't contain spaces");
            }
        } else {
            handleResult.setMessage("Email can't be empty");
        }

        return handleResult;
    }
}
