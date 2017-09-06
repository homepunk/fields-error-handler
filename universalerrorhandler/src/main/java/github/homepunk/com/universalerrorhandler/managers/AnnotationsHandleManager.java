package github.homepunk.com.universalerrorhandler.managers;

import android.util.Log;

import com.homepunk.github.models.UniversalFieldType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import github.homepunk.com.universalerrorhandler.handlers.fields.FieldsErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;

import static android.content.ContentValues.TAG;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class AnnotationsHandleManager {
    private static AnnotationsHandleManager instance;
    //    private Object target;
    private Map<Class, Object> destinationTargetMap;
    private Map<Class, Object> destinationMap;

    private AnnotationsHandleManager() {
        destinationMap = new HashMap();
        destinationTargetMap = new HashMap();
    }

    static AnnotationsHandleManager target(Object target) {
        if (instance == null) {
            instance = new AnnotationsHandleManager();
        }

        instance.addTarget(target);
        return instance;
    }

    static AnnotationsHandleManager destination(Object destination) {
        if (instance == null) {
            instance = new AnnotationsHandleManager();
        }

        instance.addDestination(destination);
        return instance;
    }

    private void addTarget(Object target) {
        createFieldHandlerInstance(target);

        try {
            Class<?> generatedClass = Class.forName(target.getClass().getName() + "_FieldHandler");
            Field destinationField = generatedClass.getDeclaredField("destination");

            if (destinationField != null) {
                if (destinationTargetMap.containsKey(destinationField.getType())) {
                    invokeSetDestinationMethod(generatedClass, destinationMap.get(destinationField.getType()));
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private AnnotationsHandleManager addDestination(Object destination) {
        if (destinationTargetMap.isEmpty()) {
            destinationTargetMap.put(destination.getClass(), null);
            destinationMap.put(destination.getClass(), destination);
        } else {
            Class target = destinationTargetMap.get(destination.getClass()).getClass();
            invokeSetDestinationMethod(target, destination);
        }

        return this;
    }

    private void invokeSetDestinationMethod(Object target, Object destination) {
        try {
//              expected receiver of type github.homepunk.com.example.views.LoginActivity_FieldHandler, but got github.homepunk.com.example.presenters.LoginActivityPresenter
            if (target instanceof Class) {
                Method setDestinationMethod = ((Class)target).getDeclaredMethod("setDestination", destination.getClass());
                setDestinationMethod.invoke(destinationTargetMap.get(destination.getClass()), destination);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public AnnotationsHandleManager addFieldValidator(@UniversalFieldType int fieldType, UniversalValidator validator) {
        FieldsErrorHandler.getInstance().setFieldValidator(fieldType, validator);
        return this;
    }

    private void createFieldHandlerInstance(Object target) {
        Constructor constructor = findConstructorForClass(target.getClass());

        try {
            Object targetInstance = constructor.newInstance(target);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Constructor findConstructorForClass(Class<?> targetClass) {
        Constructor bindingConstructor = null;
        String targetClassName = targetClass.getName();
        try {
            Class<?> bindingClass = targetClass.getClassLoader().loadClass(targetClassName + "_FieldHandler");
            //noinspection unchecked
            bindingConstructor = bindingClass.getConstructor(targetClass);
            Log.d(TAG, "HIT: Loaded binding class and constructor.");
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "Not found. Trying superclass " + targetClass.getSuperclass().getName());
            bindingConstructor = findConstructorForClass(targetClass.getSuperclass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding constructor for " + targetClassName, e);
        }

        return bindingConstructor;
    }

}
