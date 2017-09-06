package github.homepunk.com.universalerrorhandler.handlers.fields.validators;

import android.util.Patterns;

import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;

/**
 * Created by homepunk on 26.08.17.
 */

public class PhoneValidator implements UniversalValidator {
    public boolean isValid(String phone) {
        return phone.length() > 2 && Patterns.PHONE.matcher(phone).matches();
    }
}
