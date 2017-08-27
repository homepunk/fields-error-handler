package github.homepunk.com.universalerrorhandler;

import android.widget.EditText;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import github.homepunk.com.universalerrorhandler.handlers.fields.FieldsHandleManager;
import github.homepunk.com.universalerrorhandler.handlers.fields.listeneres.HandleFailListener;
import github.homepunk.com.universalerrorhandler.handlers.fields.listeneres.HandleSuccessListener;
import github.homepunk.com.universalerrorhandler.handlers.fields.listeneres.FieldsHandleListener;
import github.homepunk.com.universalerrorhandler.handlers.requests.RequestsHandleManager;
import github.homepunk.com.universalerrorhandler.handlers.requests.listeners.RequestsHandleListener;
import github.homepunk.com.universalerrorhandler.models.UniversalFieldType;

/**
 * Created by homepunk on 25.08.17.
 */

public class HandleManager {
    private static HandleManager instance;
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
        fieldsHandleManager.setOnFailListener(new HandleFailListener() {
            @Override
            public void onHandleFailed(String error) {
                if (fieldsHandleListener != null) {
                    fieldsHandleListener.onFieldHandleResult(targetType, false, error);
                }
            }
        });
        fieldsHandleManager.setOnSuccessListener(new HandleSuccessListener() {
            @Override
            public void onHandleSuccess() {
                if (fieldsHandleListener != null) {
                    fieldsHandleListener.onFieldHandleResult(targetType, true, null);
                }
            }
        });

        return fieldsHandleManager;
    }
}
