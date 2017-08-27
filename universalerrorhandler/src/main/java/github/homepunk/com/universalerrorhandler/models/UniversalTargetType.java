package github.homepunk.com.universalerrorhandler.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.CREDIT_CARD;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.CUSTOM;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.EMAIL;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.NAME;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.NETWORK_REQUESTS;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.PASSWORD;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.PHONE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by homepunk on 24.08.17.
 */

@Retention(RUNTIME)
@IntDef({NAME, EMAIL, PHONE, CUSTOM, PASSWORD, CREDIT_CARD, NETWORK_REQUESTS})
public @interface UniversalTargetType {
    int NAME = 1;
    int EMAIL = 2;
    int PHONE = 3;
    int CUSTOM = 4;
    int PASSWORD = 5;
    int CREDIT_CARD = 6;
    int NETWORK_REQUESTS = 7;
}
