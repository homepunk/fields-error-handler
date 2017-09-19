package github.homepunk.com.example.auth.registration;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.homepunk.github.HandleField;
import com.homepunk.github.OnFieldHandleResult;

import github.homepunk.com.example.R;
import github.homepunk.com.universalerrorhandler.managers.UniversalHandleManager;

import static com.homepunk.github.models.UniversalFieldAction.ON_TEXT_CHANGE;
import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD_CONFIRMATION;

public class RegistrationFragment extends Fragment {
    @HandleField(EMAIL)
    EditText mEmailEditText;
    @HandleField(PASSWORD)
    EditText mPasswordEditText;
    @HandleField(value = PASSWORD_CONFIRMATION, action = ON_TEXT_CHANGE)
    EditText mPasswordConfirmationEditText;
    TextInputLayout mEmailInputLayout;
    TextInputLayout mPasswordInputLayout;
    TextInputLayout mPasswordConfirmationInputLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, container, false);
        bindViews(root);

        UniversalHandleManager.target(this);
        UniversalHandleManager.destination(this);

        return root;
    }

    private void bindViews(View root) {
        mEmailEditText = root.findViewById(R.id.fragment_registration_email_edit_text);
        mEmailInputLayout = root.findViewById(R.id.fragment_registration_email_input_layout);
        mPasswordEditText = root.findViewById(R.id.fragment_registration_password_edit_text);
        mPasswordInputLayout = root.findViewById(R.id.fragment_registration_password_input_layout);
        mPasswordConfirmationEditText = root.findViewById(R.id.fragment_registration_password_confirmation_edit_text);
        mPasswordConfirmationInputLayout = root.findViewById(R.id.fragment_registration_password_confirmation_input_layout);
    }

    @OnFieldHandleResult("RegistrationFragment")
    public void onFieldsHandleResult(int fieldType, boolean isSuccess) {
        switch (fieldType) {
            case EMAIL: {
                handleResult(mEmailInputLayout, isSuccess, "Email not correct");
                break;
            }
            case PASSWORD: {
                handleResult(mPasswordConfirmationInputLayout, isSuccess, "Password can't be empty");
                break;
            }
            case PASSWORD_CONFIRMATION: {
                handleResult(mPasswordConfirmationInputLayout, isSuccess, "Password don't match");
            }
        }
    }

    private void handleResult(TextInputLayout inputLayout, boolean isSuccess, String message) {
        inputLayout.setError(isSuccess ? "" : message);
    }
}
