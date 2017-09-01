package github.homepunk.com.universalerrorhandler.handlers;

import android.app.Activity;

/**
 * Created by Homepunk on 01.09.2017.
 **/

public abstract class BaseFieldHandler<A extends Activity> {
    protected A targetActivity;

    public BaseFieldHandler(A target) {
        targetActivity = target;
    }

    protected abstract void handleField();
}
