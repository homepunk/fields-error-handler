package github.homepunk.com.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.homepunk.github.HandleField;
import com.homepunk.github.models.UniversalFieldType;

import github.homepunk.com.example.interfaces.LoginExamplePresenter;
import github.homepunk.com.example.interfaces.LoginExampleView;
import github.homepunk.com.universalerrorhandler.handlers.fields.interfaces.FieldsHandleListener;
import github.homepunk.com.universalerrorhandler.managers.UniversalHandleManager;

import static com.homepunk.github.models.UniversalFieldAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.UniversalFieldAction.ON_TEXT_CHANGE;
import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.NAME;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD;

public class LoginExampleActivity extends AppCompatActivity implements LoginExampleView {
    @HandleField(value = PASSWORD, action = ON_TEXT_CHANGE)
    EditText passwordEditText;
    @HandleField(value = EMAIL, action = ON_FOCUS_MISS)
    EditText emailEditText;
    @HandleField(value = NAME, action = ON_TEXT_CHANGE)
    EditText nameEditText;
    TextInputLayout emailInputLayout;
    TextInputLayout passwordInputLayout;

    private LoginExamplePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_example);
        init();
        UniversalHandleManager.bind(this);
        UniversalHandleManager.setFieldsHandleListener(new FieldsHandleListener() {
            @Override
            public void onFieldHandleResult(@UniversalFieldType int targetType, boolean isSuccess, String error) {
                if (isSuccess) {
                    handleFieldSuccess(targetType);
                } else {
                    handleFieldError(targetType);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.terminate();
        super.onDestroy();
    }

    public void handleFieldError(int errorType) {
        switch (errorType) {
            case EMAIL: {
                emailInputLayout.setError("Email can't be empty");
                break;
            }
            case PASSWORD: {
                passwordInputLayout.setError("Password can't be empty");
                break;
            }
        }
    }

    private void handleFieldSuccess(int fieldType) {
        switch (fieldType) {
            case EMAIL: {
                emailInputLayout.setError("");
                break;
            }
            case PASSWORD: {
                passwordInputLayout.setError("");
                break;
            }
        }
    }


    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    private void init() {
        emailEditText = findViewById(R.id.auth_login_email_edit_text);
        passwordEditText = findViewById(R.id.auth_login_password_edit_text);
        emailInputLayout = findViewById(R.id.auth_login_email_input_layout);
        passwordInputLayout = findViewById(R.id.auth_login_password_input_layout);
        presenter = new LoginExampleActivityPresenter();
        presenter.bind(this);
    }
}
