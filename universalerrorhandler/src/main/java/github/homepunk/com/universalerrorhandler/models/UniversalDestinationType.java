package github.homepunk.com.universalerrorhandler.models;

/**
 * Created by homepunk on 25.08.17.
 */

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;

import static github.homepunk.com.universalerrorhandler.models.UniversalDestinationType.FIELDS;
import static github.homepunk.com.universalerrorhandler.models.UniversalDestinationType.REQUESTS;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({FIELDS, REQUESTS})
public @interface UniversalDestinationType {
    int FIELDS = 1;
    int REQUESTS = 2;
}
