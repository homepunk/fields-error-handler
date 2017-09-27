package github.homepunk.com.fieldserrorhandler.handlers;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.homepunk.github.models.TargetAction;
import com.homepunk.github.models.TargetType;

import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleErrorResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.listeners.HandleSuccessResultListener;
import github.homepunk.com.fieldserrorhandler.handlers.validators.interfaces.Handler;
import github.homepunk.com.fieldserrorhandler.wrappers.TextWatcherWrapper;

import static com.homepunk.github.models.TargetAction.AFTER_TEXT_CHANGE;
import static com.homepunk.github.models.TargetAction.BEFORE_TEXT_CHANGE;
import static com.homepunk.github.models.TargetAction.NO_ACTION;
import static com.homepunk.github.models.TargetAction.ON_CLICK;
import static com.homepunk.github.models.TargetAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.TargetAction.ON_TEXT_CHANGE;

/**
 * Created by Homepunk on 19.09.2017.
 **/

public class HandleActionsManager {
    private int targetType;
    private int targetAction;
    private EditText target;
    private HandleManager handleManager;

    public HandleActionsManager(EditText target, int targetType, HandleManager handleManager) {
        this.target = target;
        this.targetType = targetType;
        this.targetAction = NO_ACTION;
        this.handleManager = handleManager;
    }

    public void addHandleAction(@TargetAction int action) {
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

    public int getHandleAction() {
        return targetAction;
    }

    public void setOnFailListener(HandleErrorResultListener listener) {
        handleManager.setOnHandleFailListener(listener);
    }

    public void setOnSuccessListener(HandleSuccessResultListener listener) {
        handleManager.setOnHandleSuccessListener(listener);
    }

    public void setHandleListener(HandleResultListener listener) {
        handleManager.setOnHandleResultListener(listener);
    }

    private void handle(String target, @TargetType int targetType) {
        if (target.length() > 0) {
            handleManager.handle(targetType, target);
        }
    }

    public void setFieldValidator(Handler validator) {
        handleManager.setFieldValidator(targetType, validator);
    }
}
