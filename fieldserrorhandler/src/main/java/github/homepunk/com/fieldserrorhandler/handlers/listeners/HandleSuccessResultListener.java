package github.homepunk.com.fieldserrorhandler.handlers.listeners;

import com.homepunk.github.models.TargetType;

/**
 * Created by homepunk on 27.08.17.
 */

public interface HandleSuccessResultListener {
    void onSuccess(@TargetType int targetType);
}
