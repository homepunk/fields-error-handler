package github.homepunk.com.universalerrorhandler.handlers.listeners;

import com.homepunk.github.models.UniversalFieldType;

/**
 * Created by homepunk on 27.08.17.
 */

public interface SuccessResultListener {
    void onSuccess(@UniversalFieldType int targetType);
}
