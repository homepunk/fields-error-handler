package github.homepunk.com.universalerrorhandler.handlers.fields.validators;

import android.util.Patterns;

import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class EmailValidator implements UniversalValidator {
    @Override
    public boolean isValid(String target) {
        return target.length() > 6 && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
