package github.homepunk.com.fieldserrorhandler.handlers.fields.validators;

import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.interfaces.TargetValidator;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

import static com.homepunk.github.models.TargetType.PASSWORD;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class PasswordValidator implements TargetValidator {
    @Override
    public HandleResult isValid(String target) {
        HandleResult handleResult = new HandleResult();
        handleResult.setTargetType(PASSWORD);
        handleResult.setSuccess(true);
        if (target.length() > 0) {
            if (target.contains(" ")) {
                handleResult.setSuccess(false);
                handleResult.setMessage("Password can't contain spaces");
            }
        } else {
            handleResult.setSuccess(false);
            handleResult.setMessage("Password can't be empty");
        }

        return handleResult;
    }
}
