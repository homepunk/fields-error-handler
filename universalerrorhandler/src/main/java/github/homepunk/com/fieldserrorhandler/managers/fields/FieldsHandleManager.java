package github.homepunk.com.universalerrorhandler.managers.fields;

import android.util.SparseArray;
import android.widget.EditText;

import com.homepunk.github.models.UniversalFieldAction;
import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.universalerrorhandler.handlers.fields.FieldsErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;
import github.homepunk.com.universalerrorhandler.handlers.listeners.FieldsHandleListener;

import static com.homepunk.github.models.UniversalFieldAction.NO_ACTION;
import static com.homepunk.github.models.UniversalFieldAction.ON_FOCUS_MISS;


/**
 * Created by homepunk on 26.08.17.
 */

public class FieldsHandleManager {
    private final FieldsErrorHandler currentFieldsErrorHandler;
    private int currentTargetType;
    private EditText currentTargetField;
    private SparseArray<String> targetDefaultMaskMap;
    private SparseArray<FieldsActionManager> targetActionManagersMap;

    public FieldsHandleManager(FieldsErrorHandler fieldsErrorHandler) {
        currentFieldsErrorHandler = fieldsErrorHandler;
        targetActionManagersMap = new SparseArray<>();
        targetDefaultMaskMap = new SparseArray<>();
    }

    public FieldsHandleManager target(EditText targetField, @UniversalFieldType int targetType) {
        setupDefaultHandleAction();
        currentTargetType = targetType;
        currentTargetField = targetField;
        FieldsActionManager fieldsActionManager = new FieldsActionManager(targetField, targetType, currentFieldsErrorHandler);
        targetActionManagersMap.put(currentTargetType, fieldsActionManager);
        return this;
    }

    public FieldsHandleManager target(EditText targetField, @UniversalFieldType int targetType, UniversalValidator validator) {
        target(targetField, targetType);
        validate(validator);
        return this;
    }

    public FieldsHandleManager handleOnAction(@UniversalFieldAction int action) {
        if (targetActionManagersMap.get(currentTargetType) != null) {
            targetActionManagersMap.get(currentTargetType).setHandleAction(action);
        }

        return this;
    }

    public FieldsHandleManager setDefaultMask(String defaultMask, char substitueSymbol) {
        targetDefaultMaskMap.put(currentTargetType, defaultMask);
        return this;
    }

    public FieldsHandleManager validate(UniversalValidator validator) {
        if (targetActionManagersMap.get(currentTargetType) != null) {
            targetActionManagersMap.get(currentTargetType).setFieldValidator(validator);
        }
        return this;
    }

    public void setHandleListener(FieldsHandleListener listener) {
        setupDefaultHandleAction();
        if (targetActionManagersMap.get(currentTargetType) != null) {
            targetActionManagersMap.get(currentTargetType).setHandleListener(listener);
        }
    }

    private void setupDefaultHandleAction() {
        FieldsActionManager currentFieldActionManager = targetActionManagersMap.get(currentTargetType);
        if (currentFieldActionManager != null && currentFieldActionManager.getHandleAction() == NO_ACTION) {
            currentFieldActionManager.setHandleAction(ON_FOCUS_MISS);
        }
    }


}
