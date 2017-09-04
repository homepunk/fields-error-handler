package github.homepunk.com.universalerrorhandler.managers;

import android.util.Log;
import android.widget.EditText;

import com.homepunk.github.models.UniversalFieldType;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import github.homepunk.com.universalerrorhandler.handlers.BaseFieldHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.interfaces.FieldsHandleListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.SuccessResultListener;
import github.homepunk.com.universalerrorhandler.handlers.requests.listeners.RequestsHandleListener;

import static android.content.ContentValues.TAG;

/**
 * Created by homepunk on 25.08.17.
 */

public abstract class UniversalHandleManager {
    private static FieldsHandleListener fieldsHandleListener;
    private static RequestsHandleListener requestsHandleListener;

    public static void bind(Object target) {
        Constructor<? extends BaseFieldHandler> constructor = findConstructorForClass(target.getClass());

        try {
            constructor.newInstance(target);
//            Log.i(UniversalHandleManager.class.getSimpleName(), "CONSTRUCTOR CREATED");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Constructor<? extends BaseFieldHandler> findConstructorForClass(Class<?> targetClass) {
        Constructor<? extends BaseFieldHandler> bindingConstructor = null;
        String targetClassName = targetClass.getName();
        try {
            Class<?> bindingClass = targetClass.getClassLoader().loadClass(targetClassName + "_FieldHandler");
            //noinspection unchecked
            bindingConstructor = (Constructor<? extends BaseFieldHandler>) bindingClass.getConstructor(targetClass);
            Log.d(TAG, "HIT: Loaded binding class and constructor.");
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "Not found. Trying superclass " + targetClass.getSuperclass().getName());
            bindingConstructor = findConstructorForClass(targetClass.getSuperclass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding constructor for " + targetClassName, e);
        }

        return bindingConstructor;
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

    public static RequestsHandleManager target(Response targetResponse) {
        return null;
    }

    public static RequestsHandleManager target(ResponseBody targetResponse) {
        return null;
    }

    public static void setFieldsHandleListener(final FieldsHandleListener listener) {
        fieldsHandleListener = listener;
    }

    public static void setRequestsHandleListener(final RequestsHandleListener listener) {
        requestsHandleListener = listener;
    }
}
