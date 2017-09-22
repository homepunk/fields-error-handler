package github.homepunk.com.universalerrorhandler.managers.annotations;

import android.util.Log;

import com.homepunk.github.models.UniversalFieldType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;

import static android.content.ContentValues.TAG;

/**
 * Created by Homepunk on 05.09.2017.
 **/

public class AnnotationsHandleManager {
    private static AnnotationsHandleManager instance;
    private Object generatedInstance;
    private Object destination;

    private AnnotationsHandleManager() {}

    public static AnnotationsHandleManager target(Object target) {
        if (instance == null) {
            instance = new AnnotationsHandleManager();
        }

        instance.setTarget(target);
        return instance.isGeneratedInstanceExists(target) ? instance : null;
    }

    private boolean isGeneratedInstanceExists(Object target) {
        try {
            return Class.forName(target.getClass().getName() + "_FieldHandler") != null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static AnnotationsHandleManager destination(Object destination) {
        if (instance == null) {
            instance = new AnnotationsHandleManager();
        }

        instance.setDestination(destination);
        return instance;
    }

    private AnnotationsHandleManager setTarget(Object target) {
        createTargetFieldsHandlerInstance(target);

        try {
            Class<?> generatedClass = Class.forName(target.getClass().getName() + "_FieldHandler");
            Field destinationField = generatedClass.getDeclaredField("bindDestination");
            if (destinationField != null && generatedInstance != null) {
                if (destination != null) {
                    invokeSetDestinationMethod(generatedInstance, destination);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return this;
    }

    private AnnotationsHandleManager setDestination(Object destination) {
        if (generatedInstance != null) {
            invokeSetDestinationMethod(generatedInstance, destination);
        }
        this.destination = destination;
        return this;
    }

    private void invokeSetDestinationMethod(Object generatedInstance, Object destination) {
        try {
            Method setDestinationMethod = generatedInstance.getClass().getDeclaredMethod("setDestination", destination.getClass());
            setDestinationMethod.invoke(generatedInstance, destination);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public AnnotationsHandleManager validateField(@UniversalFieldType int fieldType, UniversalValidator validator) {
        // TODO: 19.09.2017
        return this;
    }

    private void createTargetFieldsHandlerInstance(Object target) {
        Constructor constructor = findConstructorForClass(target.getClass());

        try {
            generatedInstance = constructor.newInstance(target);
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
