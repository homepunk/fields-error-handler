package github.homepunk.com.universalerrorhandler;

import android.widget.EditText;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import github.homepunk.com.universalerrorhandler.managers.FieldsHandleManager;
import github.homepunk.com.universalerrorhandler.managers.RequestsHandleManager;
import github.homepunk.com.universalerrorhandler.models.UniversalTargetType;

/**
 * Created by homepunk on 25.08.17.
 */

public class ErrorHandleManager {
    public static FieldsHandleManager target(EditText targetField, @UniversalTargetType int targetType) {
        return new FieldsHandleManager(targetField, targetType);
    }

    public static RequestsHandleManager target(ResponseBody targetResponse) {
        return new RequestsHandleManager(targetResponse);
    }

    public static RequestsHandleManager target(Response targetResponse) {
        return new RequestsHandleManager(targetResponse);
    }
}
