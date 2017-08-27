package github.homepunk.com.universalerrorhandler.annotations;


import java.lang.annotation.Retention;
import github.homepunk.com.universalerrorhandler.models.UniversalResultType;

import static java.lang.annotation.RetentionPolicy.*;

/**
 * Created by homepunk on 25.08.17.
 */
@Retention(RUNTIME)
public @interface HandleResultDestination {
    @UniversalResultType int value();
}
