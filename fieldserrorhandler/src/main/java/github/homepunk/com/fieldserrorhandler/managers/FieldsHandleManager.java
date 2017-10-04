package github.homepunk.com.fieldserrorhandler.managers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.widget.EditText;

import com.homepunk.github.models.TargetAction;
import com.homepunk.github.models.TargetType;

import github.homepunk.com.fieldserrorhandler.builders.FieldsHandler;
import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.exceptions.TargetNotBoundException;
import github.homepunk.com.fieldserrorhandler.handlers.HandleActionsManager;
import github.homepunk.com.fieldserrorhandler.handlers.HandleManager;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleResultListener;

import static com.homepunk.github.models.TargetAction.NO_ACTION;
import static com.homepunk.github.models.TargetAction.ON_FOCUS_MISS;


/**
 * Created by homepunk on 26.08.17.
 */

public class FieldsHandleManager {
    private static SparseArray<AnnotationsManager> targetAnnotationsManagersMap;
    private static SparseArray<FieldsHandleManager> targetFieldsHandleManagersMap;

    static {
        targetAnnotationsManagersMap = new SparseArray<>();
        targetFieldsHandleManagersMap = new SparseArray<>();
    }

    private final HandleManager currentHandleManager;
    private final SparseArray<HandleActionsManager> targetActionManagersMap;
    private int currentTargetType;

    private FieldsHandleManager(HandleManager handleManager) {
        currentHandleManager = handleManager;
        targetActionManagersMap = new SparseArray<>();
    }

    public static FieldsHandleManager getInstance(Activity target) {
        return retrieveFieldsHandleManager(target.hashCode(), target);
    }

    public static FieldsHandleManager getInstance(Fragment target) {
        return retrieveFieldsHandleManager(target.hashCode(), target.getContext());
    }

    private static FieldsHandleManager retrieveFieldsHandleManager(int key, Context context) {
        if (targetFieldsHandleManagersMap.get(key) == null) {
            targetFieldsHandleManagersMap.put(key, new FieldsHandleManager(new HandleManager(context)));
        }

        return targetFieldsHandleManagersMap.get(key);
    }

    public static void bind(Activity target) {
        targetAnnotationsManagersMap.put(target.hashCode(), AnnotationsManager.init(target, target));
    }

    public static void bind(Fragment target) {
        targetAnnotationsManagersMap.put(target.hashCode(), AnnotationsManager.init(target, target));
    }

    public static void bind(Activity target, Object destination) {
        targetAnnotationsManagersMap.put(target.hashCode(), AnnotationsManager.init(target, destination));
    }

    public static void bind(Fragment target, Object destination) {
        targetAnnotationsManagersMap.put(target.hashCode(), AnnotationsManager.init(target, destination));
    }

    public FieldsHandleManager target(EditText targetField, @TargetType int targetType) {
        setupDefaultHandleAction();
        currentTargetType = targetType;
        targetActionManagersMap.put(currentTargetType, new HandleActionsManager(targetField, targetType, currentHandleManager));
        return this;
    }

    public FieldsHandleManager target(EditText targetField, @TargetType int targetType, @TargetAction int... actions) {
        target(targetField, targetType);
        handleOnActions(actions);
        return this;
    }

    public FieldsHandleManager target(EditText targetField, int targetType, TargetHandler handler) {
        target(targetField, targetType);
        currentHandleManager.addTargetFieldHandler(targetType, handler);
        return this;
    }

    public FieldsHandleManager target(EditText targetField, int targetType, TargetHandler handler, @TargetAction int... actions) {
        target(targetField, targetType, actions);
        currentHandleManager.addTargetFieldHandler(targetType, handler);
        return this;
    }

    public void setOnHandleResultListener(HandleResultListener listener) {
        setupDefaultHandleAction();
        if (targetActionManagersMap.get(currentTargetType) != null) {
            targetActionManagersMap.get(currentTargetType).setHandleListener(listener);
        }
    }

    private void setupDefaultHandleAction() {
        HandleActionsManager currentFieldActionManager = targetActionManagersMap.get(currentTargetType);
        if (currentFieldActionManager != null && currentFieldActionManager.getHandleAction() == NO_ACTION) {
            currentFieldActionManager.addHandleAction(ON_FOCUS_MISS);
        }
    }

    private FieldsHandleManager handleOnActions(@TargetAction int... actions) {
        if (targetActionManagersMap.get(currentTargetType) != null) {
            HandleActionsManager actionManager = targetActionManagersMap.get(currentTargetType);
            if (actions != null) {
                for (int action : actions) {
                    actionManager.addHandleAction(action);
                }
            }
        }
        return this;
    }

    public static FieldsHandler getFieldsHandler(Activity target) throws TargetNotBoundException {
        AnnotationsManager manager = targetAnnotationsManagersMap.get(target.hashCode());
        if (manager != null) {
            return new FieldsHandler(manager);
        } else {
            throw new TargetNotBoundException();
        }
    }

    public static FieldsHandler getFieldsHandler(Fragment target) throws TargetNotBoundException {
        AnnotationsManager manager = targetAnnotationsManagersMap.get(target.hashCode());
        if (manager != null) {
            return new FieldsHandler(manager);
        } else {
            throw new TargetNotBoundException();
        }
    }
}
