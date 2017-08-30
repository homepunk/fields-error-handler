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

import github.homepunk.com.example.interfaces.LoginExamplePresenter;
import github.homepunk.com.example.interfaces.LoginExampleView;

import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD;

public class LoginExampleActivity extends AppCompatActivity implements LoginExampleView {
    @HandleField(EMAIL)
    EditText emailEditText;
    @HandleField(PASSWORD)
    EditText passwordEditText;
    TextInputLayout emailInputLayout;
    TextInputLayout passwordInputLayout;

    private LoginExamplePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_example);
        init();

//        UniversalHandleManager.setFieldsHandleListener((fieldType, isSuccess, error) -> {
//            if (!isSuccess) {
//                handleFieldError(fieldType);
//            } else {
//                handleFieldSuccess(fieldType);
//            }
//        });

//        UniversalHandleManager.target(emailEditText, UniversalFieldType.EMAIL).handleOnAction(ON_FOCUS_MISS);
//        UniversalHandleManager.target(passwordEditText, PASSWORD).handleOnAction(ON_TEXT_CHANGE);
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
