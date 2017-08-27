package github.homepunk.com.universalerrorhandler.handlers.fields;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import github.homepunk.com.universalerrorhandler.handlers.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.listeneres.HandleFailListener;
import github.homepunk.com.universalerrorhandler.handlers.fields.listeneres.HandleSuccessListener;
import github.homepunk.com.universalerrorhandler.models.UniversalFieldType;
import github.homepunk.com.universalerrorhandler.models.UniversalAction;
import github.homepunk.com.universalerrorhandler.wrappers.TextWatcherWrapper;

import static github.homepunk.com.universalerrorhandler.models.UniversalAction.AFTER_TEXT_CHANGE;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.BEFORE_TEXT_CHANGE;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_CLICK;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_FOCUS_MISS;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_TEXT_CHANGE;

/**
 * Created by homepunk on 26.08.17.
 */

public class FieldsHandleManager {
    private static FieldsHandleManager instance;
    private static int currentTargetType;
    private static EditText currentTargetField;
    private static Map<Integer, EditText> targetFieldMap;
    private Map<Integer, Integer> targetTypeMap;
    private Map<Integer, HandleFailListener> targetFailListenerMap;
    private Map<Integer, HandleSuccessListener> targetSuccessListenerMap;

    private UniversalErrorHandler fieldsErrorHandler;

    private FieldsHandleManager() {
        targetTypeMap = new HashMap<>();
        targetFieldMap = new HashMap<>();
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

    public FieldsHandleManager handleOnAction(@UniversalAction int action) {
        targetTypeMap.put(action, currentTargetType);

        if (currentTargetField != null) {
            switch (action) {
                case ON_CLICK: {
                    currentTargetField.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int targetType = targetTypeMap.get(ON_CLICK);
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
                            int targetType = targetTypeMap.get(AFTER_TEXT_CHANGE);

                            handle(target.toString(), targetType);
                        }
                    });
                    break;
                }
                case ON_TEXT_CHANGE: {
                    currentTargetField.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void onTextChanged(CharSequence targetCharSequence, int i, int i1, int i2) {
                            int targetType = targetTypeMap.get(ON_TEXT_CHANGE);
                            handle(targetCharSequence.toString(), targetType);
                        }
                    });
                    break;
                }
                case BEFORE_TEXT_CHANGE: {
                    currentTargetField.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void beforeTextChanged(CharSequence targetCharSequence, int i, int i1, int i2) {
                            int targetType = targetTypeMap.get(BEFORE_TEXT_CHANGE);
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
                                int targetType = targetTypeMap.get(ON_FOCUS_MISS);
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

    public FieldsHandleManager setOnFailListener(HandleFailListener listener) {
        targetFailListenerMap.put(currentTargetType, listener);
        return this;
    }

    public FieldsHandleManager setOnSuccessListener(HandleSuccessListener listener) {
        targetSuccessListenerMap.put(currentTargetType, listener);
        return this;
    }

    private void handle(String target, @UniversalFieldType int targetType) {
        initTargetListeners(targetType);
        if (target.length() > 0) {
            fieldsErrorHandler.handle(targetType, target);
        }
    }

    private void initTargetListeners(int targetType) {
        HandleFailListener handleFailListener = targetFailListenerMap.get(targetType);
        HandleSuccessListener handleSuccessListener = targetSuccessListenerMap.get(targetType);

        if (handleFailListener != null) {
            fieldsErrorHandler.setOnHandleFailListener(handleFailListener);
        }

        if (handleSuccessListener != null) {
            fieldsErrorHandler.setOnHandleSuccessListener(handleSuccessListener);
        }
    }
}
