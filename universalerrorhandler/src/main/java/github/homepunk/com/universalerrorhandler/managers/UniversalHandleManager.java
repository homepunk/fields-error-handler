package github.homepunk.com.universalerrorhandler.managers;

import android.widget.EditText;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import github.homepunk.com.universalerrorhandler.handlers.interfaces.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.SuccessResultListener;
import github.homepunk.com.universalerrorhandler.handlers.fields.interfaces.FieldsHandleListener;
import github.homepunk.com.universalerrorhandler.handlers.requests.listeners.RequestsHandleListener;
import github.homepunk.com.universalerrorhandler.models.UniversalFieldType;

/**
 * Created by homepunk on 25.08.17.
 */

public class UniversalHandleManager {
    private static FieldsHandleListener fieldsHandleListener;
    private static RequestsHandleListener requestsHandleListener;

    public static void setFieldsHandleListener(final FieldsHandleListener listener) {
        fieldsHandleListener = listener;
    }

    public static void setRequestsHandleListener(final RequestsHandleListener listener) {
        requestsHandleListener = listener;
    }

    public static RequestsHandleManager target(Response targetResponse) {
        return null;
    }

    public static RequestsHandleManager target(ResponseBody targetResponse) {
        return null;
    }

    public static FieldsHandleManager target(EditText targetField, @UniversalFieldType final int targetType) {
        FieldsHandleManager fieldsHandleManager = FieldsHandleManager.target(targetField, targetType);
        fieldsHandleManager.setOnFailListener(new ErrorResultListener() {
            @Override
            public void onErrorResult(String errorMessage) {
                if (fieldsHandleListener != null) {
                    fieldsHandleListener.onFieldHandleResult(targetType, false, errorMessage);
                }
            }
        });
        fieldsHandleManager.setOnSuccessListener(new SuccessResultListener() {
            @Override
            public void onSuccess() {
                if (fieldsHandleListener != null) {
                    fieldsHandleListener.onFieldHandleResult(targetType, true, null);
                }
            }
        });

        return fieldsHandleManager;
    }
}
