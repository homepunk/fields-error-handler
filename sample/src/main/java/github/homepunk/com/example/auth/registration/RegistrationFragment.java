package github.homepunk.com.example.auth.registration;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import github.homepunk.com.example.R;
import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.managers.FieldsHandleManager;
import github.homepunk.com.fieldserrorhandler.models.HandleResult;

import static com.homepunk.github.models.TargetAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.TargetAction.ON_TEXT_CHANGE;
import static com.homepunk.github.models.TargetErrorType.TEXT_CONTAINS_UPPER_CASE;
import static com.homepunk.github.models.TargetType.EMAIL;
import static com.homepunk.github.models.TargetType.NAME;
import static com.homepunk.github.models.TargetType.PASSWORD;
import static com.homepunk.github.models.TargetType.PHONE;

public class RegistrationFragment extends Fragment {
    public static final int PASSWORD_VERIFICATION = 1;
    public static final int PASSWORDS_DONT_MATCH = 2;
    private EditText mName;
    private TextInputLayout mNameInputLayout;
    private EditText mSurname;
    private TextInputLayout mSurnameInputLayout;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordVerification;
    private MaskedEditText mPhone;
    private TextInputLayout mEmailInputLayout;
    private TextInputLayout mPasswordInputLayout;
    private TextInputLayout mPasswordVerificationInputLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, container, false);
        bindViews(root);

        FieldsHandleManager.getInstance(this)
                .target(mPassword, PASSWORD)
                .target(mPasswordVerification, PASSWORD_VERIFICATION, TargetHandler.getBuilder()
                        .add(PASSWORDS_DONT_MATCH, "Passwords don't match", target -> target.equals(mPassword.getText().toString()))
                        .remove(TEXT_CONTAINS_UPPER_CASE)
                        .build())
                .setOnHandleResultListener(handleResult -> {
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
                        case PASSWORD_VERIFICATION: {
                            mPasswordVerificationInputLayout.setError(resultMessage);
                            break;
                        }
                        case NAME: {
                            mNameInputLayout.setError(resultMessage);
                            break;
                        }
                        case PHONE: {
                            showError(resultMessage);
                            break;
                        }
                    }
                });

        return root;
    }


    private void bindViews(View root) {
        mName = root.findViewById(R.id.fragment_registration_name_edit_text);
        mName = root.findViewById(R.id.fragment_registration_name_edit_text);
        mNameInputLayout = root.findViewById(R.id.fragment_registration_name_input_layout);
        mSurname = root.findViewById(R.id.fragment_registration_surname_edit_text);
        mSurnameInputLayout = root.findViewById(R.id.fragment_registration_surname_input_layout);
        mPhone = root.findViewById(R.id.fragment_registration_phone_masked_edit_text);
        mEmail = root.findViewById(R.id.fragment_registration_email_edit_text);
        mEmailInputLayout = root.findViewById(R.id.fragment_registration_email_input_layout);
        mPassword = root.findViewById(R.id.fragment_registration_password_edit_text);
        mPasswordInputLayout = root.findViewById(R.id.fragment_registration_password_input_layout);
        mPasswordVerification = root.findViewById(R.id.fragment_registration_password_confirmation_edit_text);
        mPasswordVerificationInputLayout = root.findViewById(R.id.fragment_registration_password_confirmation_input_layout);
    }

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
            case PASSWORD_VERIFICATION: {
                mPasswordVerificationInputLayout.setError(resultMessage);
                break;
            }
            case NAME: {
                mNameInputLayout.setError(resultMessage);
                break;
            }
            case PHONE: {
                showError(resultMessage);
                break;
            }
        }
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
