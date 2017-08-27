package github.homepunk.com.universalerrorhandler.models;

/**
 * Created by homepunk on 25.08.17.
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static github.homepunk.com.universalerrorhandler.models.UniversalResultType.FIELD;
import static github.homepunk.com.universalerrorhandler.models.UniversalResultType.REQUEST;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({FIELD, REQUEST})
public @interface UniversalResultType {
    int FIELD = 1;
    int REQUEST = 2;
}
