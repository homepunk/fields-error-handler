package github.homepunk.com.universalerrorhandler.managers.fields;

import android.util.SparseArray;
import android.widget.EditText;

import com.homepunk.github.models.UniversalFieldAction;
import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.universalerrorhandler.handlers.fields.FieldsErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;
import github.homepunk.com.universalerrorhandler.handlers.listeners.FieldsHandleListener;


/**
 * Created by homepunk on 26.08.17.
 */

public class FieldsHandleManager {
    private final FieldsErrorHandler currentFieldsErrorHandler;
    private int currentTargetType;
    private EditText currentTargetField;
    private SparseArray<String> targetDefaultMaskMap;
    private SparseArray<FieldsActionManager> targetTypeActionMap;

    public FieldsHandleManager(FieldsErrorHandler fieldsErrorHandler) {
        this.currentFieldsErrorHandler = fieldsErrorHandler;
        targetTypeActionMap = new SparseArray<>();
        targetDefaultMaskMap = new SparseArray<>();
    }

    public FieldsHandleManager target(EditText targetField, @UniversalFieldType int targetType) {
        currentTargetType = targetType;
        currentTargetField = targetField;
        FieldsActionManager fieldsActionManager = new FieldsActionManager(currentTargetField, currentTargetType, currentFieldsErrorHandler);
        targetTypeActionMap.put(currentTargetType, fieldsActionManager);
        return this;
    }

    public FieldsHandleManager handleOnAction(@UniversalFieldAction int action) {
        if (targetTypeActionMap.get(currentTargetType) != null) {
            targetTypeActionMap.get(currentTargetType).setHandleAction(action);
        }

        return this;
    }

    public void setHandleListener(FieldsHandleListener listener) {
        if (targetTypeActionMap.get(currentTargetType) != null) {
            targetTypeActionMap.get(currentTargetType).setHandleListener(listener);
        }
    }

    public FieldsHandleManager setDefaultMask(String defaultMask, char substitueSymbol) {
        targetDefaultMaskMap.put(currentTargetType, defaultMask);
        return this;
    }

    public FieldsHandleManager validate(UniversalValidator validator) {
        if (targetTypeActionMap.get(currentTargetType) != null) {
            targetTypeActionMap.get(currentTargetType).setFieldValidator(validator);
        }
        return this;
    }


}
