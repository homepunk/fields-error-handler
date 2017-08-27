package github.homepunk.com.universalerrorhandler.annotations;


import java.lang.annotation.Retention;
import github.homepunk.com.universalerrorhandler.models.UniversalDestinationType;

import static java.lang.annotation.RetentionPolicy.*;

/**
 * Created by homepunk on 25.08.17.
 */
@Retention(RUNTIME)
public @interface UniversalDestination {
    @UniversalDestinationType int value();
}
