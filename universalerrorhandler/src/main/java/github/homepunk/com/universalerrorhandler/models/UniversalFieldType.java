package github.homepunk.com.universalerrorhandler.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.CREDIT_CARD;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.CUSTOM;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.EMAIL;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.NAME;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.PASSWORD;
import static github.homepunk.com.universalerrorhandler.models.UniversalFieldType.PHONE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by homepunk on 24.08.17.
 */

@Retention(RUNTIME)
@IntDef({NAME, EMAIL, PHONE, CUSTOM, PASSWORD, CREDIT_CARD})
public @interface UniversalFieldType {
    int NAME = 1;
    int EMAIL = 2;
    int PHONE = 3;
    int CUSTOM = 4;
    int PASSWORD = 5;
    int CREDIT_CARD = 6;
}
