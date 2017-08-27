package github.homepunk.com.universalerrorhandler.managers;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import github.homepunk.com.universalerrorhandler.handlers.FieldsErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.interfaces.listeners.HandleFailListener;
import github.homepunk.com.universalerrorhandler.models.UniversalTargetType;
import github.homepunk.com.universalerrorhandler.models.UniversalAction;
import github.homepunk.com.universalerrorhandler.wrappers.TextWatcherWrapper;
import io.reactivex.functions.Action;

import static github.homepunk.com.universalerrorhandler.models.UniversalAction.AFTER_TEXT_CHANGE;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.BEFORE_TEXT_CHANGE;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_CLICK;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_FOCUS_MISS;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_TEXT_CHANGE;

/**
 * Created by homepunk on 26.08.17.
 */

public class FieldsHandleManager {
    private int targetType;
    private EditText targetField;
    private UniversalErrorHandler fieldsErrorHandler;


    public FieldsHandleManager(EditText targetField, @UniversalTargetType int targetType) {
        this.targetType = targetType;
        this.targetField = targetField;
        this.fieldsErrorHandler = FieldsErrorHandler.getInstance();
    }

    public FieldsHandleManager handleOnAction(@UniversalAction int action) {
        if (targetField != null) {
            switch (action) {
                case ON_CLICK: {
                    targetField.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            handleOnAction(targetField.getText().toString(), targetType);
                        }
                    });
                    break;
                }
                case AFTER_TEXT_CHANGE: {
                    targetField.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void afterTextChanged(Editable target) {
                            handleOnAction(target.toString(), targetType);
                        }
                    });
                    break;
                }
                case ON_TEXT_CHANGE: {
                    targetField.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void onTextChanged(CharSequence targetCharSequence, int i, int i1, int i2) {
                            handleOnAction(targetCharSequence.toString(), targetType);
                        }
                    });
                    break;
                }
                case BEFORE_TEXT_CHANGE: {
                    targetField.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void beforeTextChanged(CharSequence targetCharSequence, int i, int i1, int i2) {
                            handleOnAction(targetCharSequence.toString(), targetType);
                        }
                    });
                    break;
                }
                case ON_FOCUS_MISS: {
                    targetField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if (!hasFocus) {
                                handleOnAction(targetField.getText().toString(), targetType);
                            }
                        }
                    });
                    break;
                }
            }
        }

        return this;
    }

    public FieldsHandleManager doOnHandleSuccess(Action action) {
        fieldsErrorHandler.doOnHandleSuccess(action);
        return this;
    }

    public FieldsHandleManager replaceSuccessClause(boolean clause) {
        return this;
    }

    public FieldsHandleManager setOnHandleFailListener(HandleFailListener listener) {
        fieldsErrorHandler.setOnHandleFailListener(listener);
        return this;
    }

    private void handleOnAction(String target, @UniversalTargetType int targetType) {
        if (target.length() > 0) {
            fieldsErrorHandler.handle(targetType, target);
        }
    }
}
