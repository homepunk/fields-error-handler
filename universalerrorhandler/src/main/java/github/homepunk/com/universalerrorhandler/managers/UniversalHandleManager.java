package github.homepunk.com.universalerrorhandler.managers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import github.homepunk.com.universalerrorhandler.handlers.fields.FieldsErrorHandler;
import github.homepunk.com.universalerrorhandler.managers.annotations.AnnotationsHandleManager;
import github.homepunk.com.universalerrorhandler.managers.fields.FieldsHandleManager;
import github.homepunk.com.universalerrorhandler.managers.requests.RequestsHandleManager;

/**
 * Created by homepunk on 25.08.17.
 */

public abstract class UniversalHandleManager {
    private static SparseArray<AnnotationsHandleManager> targetAnnotationsManagersMap;
    private static SparseArray<FieldsHandleManager> targetFieldsHandleManagersMap;

    static {
        targetAnnotationsManagersMap = new SparseArray<>();
        targetFieldsHandleManagersMap = new SparseArray<>();
    }

    private UniversalHandleManager() {}

    public static FieldsHandleManager getFieldsHandleManager(Activity target) {
        return retrieveFieldsHandleManager(target);
    }

    public static FieldsHandleManager getFieldsHandleManager(Fragment target) {
        return retrieveFieldsHandleManager(target);
    }

    private static FieldsHandleManager retrieveFieldsHandleManager(Object target) {
        final FieldsHandleManager fieldsHandleManager;
        final int key = target.hashCode();
        if (targetFieldsHandleManagersMap.get(key) == null) {
            fieldsHandleManager = new FieldsHandleManager(new FieldsErrorHandler());
            targetFieldsHandleManagersMap.put(key, fieldsHandleManager);
        }

        return targetFieldsHandleManagersMap.get(key);
    }

    public static RequestsHandleManager target(Response targetResponse) {
        return null;
    }

    public static RequestsHandleManager target(ResponseBody targetResponse) {
        return null;
    }

    public static AnnotationsHandleManager target(Activity target) {
        return retrieveAnnotationsHandleManager(target);
    }

    public static AnnotationsHandleManager target(Fragment target) {
        return retrieveAnnotationsHandleManager(target);
    }

    private static AnnotationsHandleManager retrieveAnnotationsHandleManager(Object target) {
        final int key = target.hashCode();
        if (targetAnnotationsManagersMap.get(key) == null) {
            targetAnnotationsManagersMap.put(key, AnnotationsHandleManager.target(target));
        }

        return targetAnnotationsManagersMap.get(key);
    }

    public static void destination(Object destination) {
        AnnotationsHandleManager.destination(destination);
    }
}