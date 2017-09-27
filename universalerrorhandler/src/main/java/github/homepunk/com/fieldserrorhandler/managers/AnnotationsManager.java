package github.homepunk.com.fieldserrorhandler.managers;

import android.util.Log;

import com.homepunk.github.models.TargetType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.ContentValues.TAG;

/**
 * Created by Homepunk on 05.09.2017.
 **/

class AnnotationsManager {
    private static AnnotationsManager instance;
    private Object generatedInstance;
    private Object destination;

    private AnnotationsManager() {}

    static AnnotationsManager target(Object target) {
        if (instance == null) {
            instance = new AnnotationsManager();
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

    static AnnotationsManager destination(Object destination) {
        if (instance == null) {
            instance = new AnnotationsManager();
        }

        instance.setDestination(destination);
        return instance;
    }

    private AnnotationsManager setTarget(Object target) {
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

    private AnnotationsManager setDestination(Object destination) {
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

    public AnnotationsManager validateField(@TargetType int fieldType) {
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
