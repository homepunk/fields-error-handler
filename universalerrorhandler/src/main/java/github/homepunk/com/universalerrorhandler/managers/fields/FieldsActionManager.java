package github.homepunk.com.universalerrorhandler.managers.fields;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.homepunk.github.models.UniversalFieldAction;
import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.universalerrorhandler.handlers.UniversalErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.FieldsErrorHandler;
import github.homepunk.com.universalerrorhandler.handlers.fields.validators.interfaces.UniversalValidator;
import github.homepunk.com.universalerrorhandler.handlers.listeners.ErrorResultListener;
import github.homepunk.com.universalerrorhandler.handlers.listeners.FieldsHandleListener;
import github.homepunk.com.universalerrorhandler.handlers.listeners.SuccessResultListener;
import github.homepunk.com.universalerrorhandler.wrappers.TextWatcherWrapper;

import static com.homepunk.github.models.UniversalFieldAction.AFTER_TEXT_CHANGE;
import static com.homepunk.github.models.UniversalFieldAction.BEFORE_TEXT_CHANGE;
import static com.homepunk.github.models.UniversalFieldAction.ON_CLICK;
import static com.homepunk.github.models.UniversalFieldAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.UniversalFieldAction.ON_TEXT_CHANGE;

/**
 * Created by Homepunk on 19.09.2017.
 **/

public class FieldsActionManager {
    private int targetType;
    private EditText target;
    private UniversalErrorHandler fieldsErrorHandler;

    public FieldsActionManager(EditText target, int targetType, FieldsErrorHandler fieldsErrorHandler) {
        this.target = target;
        this.targetType = targetType;
        this.fieldsErrorHandler = fieldsErrorHandler;
    }

    public void setHandleAction(@UniversalFieldAction int action) {
        if (target != null) {
            switch (action) {
                case ON_CLICK: {
                    target.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            handle(target.getText().toString(), targetType);
                        }
                    });
                    break;
                }
                case AFTER_TEXT_CHANGE: {
                    target.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void afterTextChanged(Editable target) {
                            handle(target.toString(), targetType);
                        }
                    });
                    break;
                }
                case ON_TEXT_CHANGE: {
                    target.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void onTextChanged(CharSequence targetCharSequence, int i, int i1, int i2) {
                            handle(targetCharSequence.toString(), targetType);
                        }
                    });
                    break;
                }
                case BEFORE_TEXT_CHANGE: {
                    target.addTextChangedListener(new TextWatcherWrapper() {
                        @Override
                        public void beforeTextChanged(CharSequence targetCharSequence, int i, int i1, int i2) {
                            handle(targetCharSequence.toString(), targetType);
                        }
                    });
                    break;
                }
                case ON_FOCUS_MISS: {
                    target.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if (!hasFocus) {
                                handle(target.getText().toString(), targetType);
                            }
                        }
                    });
                    break;
                }
            }
        }
    }

    public void setOnFailListener(ErrorResultListener listener) {
        fieldsErrorHandler.setOnHandleFailListener(listener);
    }

    public void setOnSuccessListener(SuccessResultListener listener) {
        fieldsErrorHandler.setOnHandleSuccessListener(listener);
    }

    public void setHandleListener(FieldsHandleListener listener) {
        fieldsErrorHandler.setOnHandleResultListener(listener);
    }

    private void handle(String target, @UniversalFieldType int targetType) {
        if (target.length() > 0) {
            fieldsErrorHandler.handle(targetType, target);
        }
    }

    public void setFieldValidator(UniversalValidator validator) {
        fieldsErrorHandler.setFieldValidator(targetType, validator);
    }
}
