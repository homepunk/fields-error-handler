package github.homepunk.com.example.auth.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import github.homepunk.com.example.R;
import github.homepunk.com.example.auth.login.interfaces.LoginPresenter;
import github.homepunk.com.example.auth.login.interfaces.LoginView;
import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.managers.FieldsHandleManager;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

import static com.homepunk.github.models.TargetType.EMAIL;
import static com.homepunk.github.models.TargetType.PASSWORD;

public class LoginFragment extends Fragment implements LoginView {
    public static final int EMAIL_CONTAIN_SPACES = 1111;
    public static final int PASSWORD_SHOULD_STARTS_WITH_UPPERCASE = 2;
    EditText mEmail;
    EditText mPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        bind(root);
        init();

        FieldsHandleManager.getInstance(this)
                .target(mEmail, EMAIL, TargetHandler.getBuilder()
                        .add(EMAIL_CONTAIN_SPACES, R.string.error_email_contain_spaces, target -> !target.contains(" "))
                        .build())
                .target(mPassword, PASSWORD, TargetHandler.getBuilder()
                        .add(PASSWORD_SHOULD_STARTS_WITH_UPPERCASE, "Password should starts with upper case", target -> Character.isUpperCase(target.charAt(0)))
                        .build())
                .setOnHandleResultListener(this::onHandleResult);

        return root;
    }

    private void init() {
        LoginPresenter presenter = new LoginActivityPresenter();
        presenter.init(this);
    }

    private void bind(View root) {
        mEmail = root.findViewById(R.id.fragment_login_email_edit_text);
        mPassword = root.findViewById(R.id.fragment_login_password_edit_text);
    }

    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHandleResult(HandleResult handleResult) {
        if (!handleResult.isSuccess()) {
            showError(handleResult.getMessage());
        }
    }
}
