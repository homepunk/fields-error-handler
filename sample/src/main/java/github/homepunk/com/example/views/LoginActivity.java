package github.homepunk.com.example.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.homepunk.github.HandleField;

import github.homepunk.com.example.R;
import github.homepunk.com.example.presenters.LoginActivityPresenter;
import github.homepunk.com.example.presenters.LoginPresenter;
import github.homepunk.com.example.views.interfaces.LoginView;
import github.homepunk.com.universalerrorhandler.managers.UniversalHandleManager;

import static com.homepunk.github.models.UniversalFieldAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.UniversalFieldAction.ON_TEXT_CHANGE;
import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD;

public class LoginActivity extends AppCompatActivity implements LoginView {
    @HandleField(value = EMAIL, action = ON_FOCUS_MISS)
    EditText emailEditText;
    @HandleField(value = PASSWORD, action = ON_TEXT_CHANGE)
    EditText passwordEditText;

    TextInputLayout emailInputLayout;
    TextInputLayout passwordInputLayout;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_example);
        init();
        UniversalHandleManager.target(this)
                .addFieldValidator(EMAIL, target -> target.length() > 8)
                .addFieldValidator(PASSWORD, target -> !target.contains(" "));
    }

    @Override
    public void onFieldsHandleResult(int fieldType) {
        switch (fieldType) {
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
        presenter = new LoginActivityPresenter();
        presenter.bind(this);
    }
}
