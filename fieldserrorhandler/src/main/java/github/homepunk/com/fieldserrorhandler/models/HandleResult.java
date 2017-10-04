package github.homepunk.com.fieldserrorhandler.models;

import com.homepunk.github.models.TargetType;

/**
 * Created by Homepunk on 21.09.2017.
 **/

public class HandleResult {
    private int targetType;
    private boolean isSuccess;
    private String message;


    public @TargetType
    int getTargetType() {
        return targetType;
    }

    public void setTargetType(@TargetType int targetType) {
        this.targetType = targetType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
