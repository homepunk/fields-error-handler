package github.homepunk.com.universalerrorhandler.handlers.listeners;

/**
 * Created by homepunk on 27.08.17.
 */

public interface RequestsHandleListener {
    void onRequestHandleResult(int requestCode, boolean isSuccess, String error);
}
