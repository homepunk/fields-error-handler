package github.homepunk.com.fieldserrorhandler.managers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.TextView;

import com.homepunk.github.models.TargetAction;
import com.homepunk.github.models.TargetType;

import github.homepunk.com.fieldserrorhandler.builders.TargetDestination;
import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.handlers.HandleActionsManager;
import github.homepunk.com.fieldserrorhandler.handlers.HandleManager;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleResultListener;

import static com.homepunk.github.models.TargetAction.NO_ACTION;
import static com.homepunk.github.models.TargetAction.ON_FOCUS_MISS;


/**
 * Created by homepunk on 26.08.17.
 */

public class FieldsHandleManager {
    private static int currentTargetHashCode;
    private final HandleManager currentHandleManager;
    private static SparseArray<AnnotationsManager> targetAnnotationsManagersMap;
    private static SparseArray<FieldsHandleManager> targetFieldsHandleManagersMap;
    private SparseArray<HandleActionsManager> targetActionManagersMap;
    private int currentTargetType;

    static {
        targetAnnotationsManagersMap = new SparseArray<>();
        targetFieldsHandleManagersMap = new SparseArray<>();
    }

    private FieldsHandleManager(HandleManager handleManager) {
        currentHandleManager = handleManager;
        targetActionManagersMap = new SparseArray<>();
    }

    public static FieldsHandleManager getInstance(Activity target) {
        return retrieveFieldsHandleManager(target.hashCode());
    }

    public static FieldsHandleManager getInstance(Fragment target) {
        return retrieveFieldsHandleManager(target.hashCode());
    }

    private static FieldsHandleManager retrieveFieldsHandleManager(int key) {
        if (targetFieldsHandleManagersMap.get(key) == null) {
            targetFieldsHandleManagersMap.put(key, new FieldsHandleManager(new HandleManager()));
        }

        return targetFieldsHandleManagersMap.get(key);
    }

    public static AnnotationsManager bindTarget(Activity target) {
        return retrieveAnnotationsHandleManager(target);
    }

    public static AnnotationsManager bindTarget(Fragment target) {
        return retrieveAnnotationsHandleManager(target);
    }

    public static void bindDestination(Activity destination) {
        if (currentTargetHashCode != 0) {
            targetAnnotationsManagersMap.get(currentTargetHashCode).destination(destination);
        }
    }

    public static void bindDestination(Fragment destination) {
        if (currentTargetHashCode != 0) {
            targetAnnotationsManagersMap.get(currentTargetHashCode).destination(destination);
        }
    }

    private static AnnotationsManager retrieveAnnotationsHandleManager(Object target) {
        currentTargetHashCode = target.hashCode();
        if (targetAnnotationsManagersMap.get(currentTargetHashCode) == null) {
            targetAnnotationsManagersMap.put(currentTargetHashCode, AnnotationsManager.target(target));
        }

        return targetAnnotationsManagersMap.get(currentTargetHashCode);
    }

    public FieldsHandleManager target(EditText targetField, @TargetType int targetType) {
        setupDefaultHandleAction();
        currentTargetType = targetType;
        targetActionManagersMap.put(currentTargetType, new HandleActionsManager(targetField, targetType, currentHandleManager));
        return this;
    }

    public FieldsHandleManager target(EditText targetField, @TargetType int targetType, @TargetAction int ... actions) {
        target(targetField, targetType);
        if (actions != null) {
            for (int action : actions) {
                handleOnAction(action);
            }
        }
        return this;
    }

    public FieldsHandleManager target(EditText targetField, int targetType, TargetHandler handler) {
        target(targetField, targetType);
        currentHandleManager.addTargetFieldHandler(targetType, handler);
        return this;
    }

    public FieldsHandleManager target(EditText targetField, int targetType, TargetHandler handler, @TargetAction int... actions) {
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

    private FieldsHandleManager handleOnAction(@TargetAction int action) {
        if (targetActionManagersMap.get(currentTargetType) != null) {
            targetActionManagersMap.get(currentTargetType).addHandleAction(action);
        }

        return this;
    }

    public FieldsHandleManager target(EditText target, int targetType, TargetDestination destinationBuilder) {
        return null;
    }

    public FieldsHandleManager target(EditText target, TextView targetErrorTextView, int name) {
        return null;
    }
}
