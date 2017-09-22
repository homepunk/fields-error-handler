package github.homepunk.com.example.auth.registration;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.homepunk.github.HandleField;
import com.homepunk.github.OnFieldsHandleResult;

import github.homepunk.com.example.R;
import github.homepunk.com.fieldserrorhandler.managers.fields.FieldsHandleManager;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

import static com.homepunk.github.models.TargetAction.ON_TEXT_CHANGE;
import static com.homepunk.github.models.TargetType.EMAIL;
import static com.homepunk.github.models.TargetType.PASSWORD;
import static com.homepunk.github.models.TargetType.PASSWORD_CONFIRMATION;

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
        FieldsHandleManager.bindTarget(this);
        FieldsHandleManager.bindDestination(this);
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

    @OnFieldsHandleResult("RegistrationFragment")
    public void onFieldsHandleResult(HandleResult handleResult) {
        String resultMessage = handleResult.getMessage();

        switch (handleResult.getTargetType()) {
            case EMAIL: {
                mEmailInputLayout.setError(resultMessage);
                break;
            }
            case PASSWORD: {
                mPasswordInputLayout.setError(resultMessage);
                break;
            }
            case PASSWORD_CONFIRMATION: {
                mPasswordConfirmationInputLayout.setError(resultMessage);
            }
        }
    }
}
