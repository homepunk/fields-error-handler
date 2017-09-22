package github.homepunk.com.fieldserrorhandler.handlers.listeners;


import com.homepunk.github.models.TargetType;

/**
 * Created by homepunk on 24.08.17.
 */

public interface HandleErrorResultListener {
    void onErrorResult(@TargetType int targetType, String errorMessage);
}
