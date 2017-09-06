package github.homepunk.com.universalerrorhandler.handlers.fields.validators;

import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;

import static android.R.attr.password;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class PasswordValidator implements UniversalValidator {
    @Override
    public boolean isValid(String target) {
        return target.length() > 3;
    }
}
