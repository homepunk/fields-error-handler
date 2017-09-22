package github.homepunk.com.fieldserrorhandler.handlers.fields.validators;

import github.homepunk.com.fieldserrorhandler.handlers.fields.validators.interfaces.TargetValidator;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

import static com.homepunk.github.models.TargetType.NAME;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class NameValidator implements TargetValidator {
    @Override
    public HandleResult isValid(String target) {
        HandleResult handleResult = new HandleResult();
        handleResult.setTargetType(NAME);
        handleResult.setSuccess(true);
        if (target.length() > 0) {
            if (target.contains(" ")) {
                handleResult.setSuccess(false);
                handleResult.setMessage("Name can't contain spaces");
            }
        } else {
            handleResult.setSuccess(false);
            handleResult.setMessage("Name can't be empty");
        }

        return handleResult;
    }
}
