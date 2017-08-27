package github.homepunk.com.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import github.homepunk.com.example.interfaces.LoginExamplePresenter;
import github.homepunk.com.example.interfaces.LoginExampleView;
import github.homepunk.com.universalerrorhandler.ErrorHandleManager;
import github.homepunk.com.universalerrorhandler.models.UniversalTargetType;
import io.reactivex.functions.Action;

import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_FOCUS_MISS;
import static github.homepunk.com.universalerrorhandler.models.UniversalAction.ON_TEXT_CHANGE;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.EMAIL;
import static github.homepunk.com.universalerrorhandler.models.UniversalTargetType.PASSWORD;

public class LoginExampleActivity extends AppCompatActivity implements LoginExampleView {
    EditText emailEditText;
    EditText passwordEditText;
    TextInputLayout emailInputLayout;
    TextInputLayout passwordInputLayout;

    private LoginExamplePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_example);
        init();

        ErrorHandleManager.target(emailEditText, EMAIL).handleOnAction(ON_FOCUS_MISS)
                .doOnHandleSuccess(new Action() {
                    @Override
                    public void run() throws Exception {
                        emailInputLayout.setError("");
                    }
                })
                .setOnHandleFailListener(this);

        ErrorHandleManager.target(emailEditText, EMAIL)
                .replaceSuccessClause(emailEditText.getText().toString().length() > 4)
                .setOnHandleFailListener(this);

        ErrorHandleManager.target(passwordEditText, PASSWORD).handleOnAction(ON_TEXT_CHANGE)
                .doOnHandleSuccess(new Action() {
                    @Override
                    public void run() throws Exception {
                        passwordInputLayout.setError("");
                    }
                })
                .setOnHandleFailListener(this);
    }

    @Override
    protected void onDestroy() {
        presenter.terminate();
        super.onDestroy();
    }

    @Override
    public void onHandleFailed(@UniversalTargetType int errorType) {
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
