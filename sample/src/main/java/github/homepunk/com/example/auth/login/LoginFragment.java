package github.homepunk.com.example.auth.login;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import github.homepunk.com.example.R;
import github.homepunk.com.universalerrorhandler.managers.UniversalHandleManager;

import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD;

public class LoginFragment extends Fragment {
    EditText mEmailEditText;
    EditText mPasswordEditText;
    TextInputLayout mEmailInputLayout;
    TextInputLayout mPasswordInputLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        bindViews(root);
        UniversalHandleManager.getFieldsHandleManager(this)
                .target(mEmailEditText, EMAIL)
                .target(mPasswordEditText, PASSWORD).validate(target -> target.length() > 1)
                .setHandleListener(this::onFieldsHandleResult);
        return root;
    }

    private void bindViews(View root) {
        mEmailEditText = root.findViewById(R.id.fragment_login_email_edit_text);
        mEmailInputLayout = root.findViewById(R.id.fragment_login_email_input_layout);
        mPasswordEditText = root.findViewById(R.id.fragment_login_password_edit_text);
        mPasswordInputLayout = root.findViewById(R.id.fragment_login_password_input_layout);
    }

    public void onFieldsHandleResult(int targetType, boolean isValid) {
        switch (targetType) {
            case EMAIL: {
                processHandleResult(mEmailInputLayout, isValid);
                break;
            }
            case PASSWORD: {
                processHandleResult(mPasswordInputLayout, isValid);
                break;
            }
        }
    }

    private void processHandleResult(TextInputLayout inputLayout, boolean isValid) {
        inputLayout.setError(isValid ? "" : "The field isn't valid");
    }
}
