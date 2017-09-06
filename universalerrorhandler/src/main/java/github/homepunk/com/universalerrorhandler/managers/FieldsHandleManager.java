package github.homepunk.com.universalerrorhandler.managers;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.homepunk.github.models.UniversalFieldAction;
import com.homepunk.github.models.UniversalFieldType;

import java.util.HashMap;
import java.util.Map;

import github.homepunk.com.universalerrorhandler.handlers.fields.FieldsErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.SuccessResultListener;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.wrappers.TextWatcherWrapper;

import static com.homepunk.github.models.UniversalFieldAction.AFTER_TEXT_CHANGE;
import static com.homepunk.github.models.UniversalFieldAction.BEFORE_TEXT_CHANGE;
import static com.homepunk.github.models.UniversalFieldAction.ON_CLICK;
import static com.homepunk.github.models.UniversalFieldAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.UniversalFieldAction.ON_TEXT_CHANGE;


/**
 * Created by homepunk on 26.08.17.
 */

public class FieldsHandleManager {
    private static FieldsHandleManager instance;
    private static int currentTargetType;
    private static EditText currentTargetField;
    private static Map<Integer, EditText> targetFieldMap;
    private Map<Integer, Integer> targetActionTypeMap;
    private Map<Integer, String> targetDefaultMaskMap;
    private Map<Integer, ErrorResultListener> targetFailListenerMap;
    private Map<Integer, SuccessResultListener> targetSuccessListenerMap;

    private UniversalErrorHandler fieldsErrorHandler;

    private FieldsHandleManager() {
        targetFieldMap = new HashMap<>();
        targetActionTypeMap = new HashMap<>();
        targetDefaultMaskMap = new HashMap<>();
        targetFailListenerMap = new HashMap<>();
        targetSuccessListenerMap = new HashMap<>();
        fieldsErrorHandler = FieldsErrorHandler.getInstance();
    }

    static FieldsHandleManager target(EditText targetField, @UniversalFieldType int targetType) {
        if (instance == null) {
            instance = new FieldsHandleManager();
        }

        currentTargetType = targetType;
        currentTargetField = targetField;
        targetFieldMap.put(targetField.hashCode(), targetField);

        return instance;
    }

    public FieldsHandleManager handleOnAction(@UniversalFieldAction int action) {
        targetActionTypeMap.put(action, currentTargetType);

        if (currentTargetField != null) {
            switch (action) {
                case ON_CLICK: {
                    currentTargetField.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int targetType = targetActionTypeMap.get(ON_CLICK);
                            EditText targetField = targetFieldMap.get(view.hashCode());

                            handle(targetField.getText().toString(), targetType);
                        }
                    });
                    break;
                }
                case AFTER_TEXT_CHANGE: {
                    currentTargetField.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void afterTextChanged(Editable target) {
                            int targetType = targetActionTypeMap.get(AFTER_TEXT_CHANGE);

                            handle(target.toString(), targetType);
                        }
                    });
                    break;
                }
                case ON_TEXT_CHANGE: {
                    currentTargetField.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void onTextChanged(CharSequence targetCharSequence, int i, int i1, int i2) {
                            int targetType = targetActionTypeMap.get(ON_TEXT_CHANGE);
                            handle(targetCharSequence.toString(), targetType);
                        }
                    });
                    break;
                }
                case BEFORE_TEXT_CHANGE: {
                    currentTargetField.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void beforeTextChanged(CharSequence targetCharSequence, int i, int i1, int i2) {
                            int targetType = targetActionTypeMap.get(BEFORE_TEXT_CHANGE);
                            handle(targetCharSequence.toString(), targetType);
                        }
                    });
                    break;
                }
                case ON_FOCUS_MISS: {
                    currentTargetField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if (!hasFocus) {
                                int targetType = targetActionTypeMap.get(ON_FOCUS_MISS);
                                EditText targetField = targetFieldMap.get(view.hashCode());
                                handle(targetField.getText().toString(), targetType);
                            }
                        }
                    });
                    break;
                }
            }
        }

        return this;
    }

    public FieldsHandleManager setOnFailListener(ErrorResultListener listener) {
        targetFailListenerMap.put(currentTargetType, listener);
        return this;
    }

    public FieldsHandleManager setOnSuccessListener(SuccessResultListener listener) {
        targetSuccessListenerMap.put(currentTargetType, listener);
        return this;
    }

    public FieldsHandleManager setDefaultMask(String defaultMask, char substitueSymbol) {
        targetDefaultMaskMap.put(currentTargetType, defaultMask);
        return this;
    }

    public FieldsHandleManager addFieldValidator(UniversalValidator validator) {
        fieldsErrorHandler.setFieldValidator(currentTargetType, validator);
        return this;
    }

    private void handle(String target, @UniversalFieldType int targetType) {
        initTargetListeners(targetType);
        if (target.length() > 0) {
            fieldsErrorHandler.handle(targetType, target);
        }
    }

    private void initTargetListeners(int targetType) {
        ErrorResultListener errorResultListener = targetFailListenerMap.get(targetType);
        SuccessResultListener successResultListener = targetSuccessListenerMap.get(targetType);

        if (errorResultListener != null) {
            fieldsErrorHandler.setOnHandleFailListener(errorResultListener);
        }

        if (successResultListener != null) {
            fieldsErrorHandler.setOnHandleSuccessListener(successResultListener);
        }
    }
}
