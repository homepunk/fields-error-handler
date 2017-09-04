package github.homepunk.com.universalerrorhandler.handlers;

/**
 * Created by Homepunk on 01.09.2017.
 **/

public abstract class BaseFieldHandler<A extends Object> {
    protected A targetActivity;

    public BaseFieldHandler(A target) {
        targetActivity = target;
    }

    protected abstract void handleField();
}
