package github.homepunk.com.example.auth.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import github.homepunk.com.example.R;
import github.homepunk.com.fieldserrorhandler.builders.TargetHandler;
import github.homepunk.com.fieldserrorhandler.managers.FieldsHandleManager;

import static com.homepunk.github.models.TargetAction.ON_FOCUS;
import static com.homepunk.github.models.TargetAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.TargetAction.ON_TEXT_CHANGE;
import static com.homepunk.github.models.TargetType.EMAIL;
import static com.homepunk.github.models.TargetType.PASSWORD;

public class LoginFragment extends Fragment {
    public static final int CODE_EMAIL_CONTAIN_SPACES = 202;
    public static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS;
    private EditText mEmail;
    private EditText mPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        bind(root);
        FieldsHandleManager.getInstance(this)
                .target(mPassword, PASSWORD, ON_FOCUS_MISS, ON_TEXT_CHANGE)
                .target(mEmail, EMAIL, TargetHandler.getBuilder()
                                .add(CODE_EMAIL_CONTAIN_SPACES, R.string.error_email_contain_spaces, target -> !target.contains(" "))
                                .build(),
                        ON_FOCUS_MISS, ON_FOCUS)
                .setOnHandleResultListener(result -> {
                    if (!result.isSuccess()) {
                        showError(result.getMessage());
                    }
                });

        return root;
    }

    private void bind(View root) {
        mEmail = root.findViewById(R.id.fragment_login_email_edit_text);
        mPassword = root.findViewById(R.id.fragment_login_password_edit_text);
    }

    public void showError(String message) {
        Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
