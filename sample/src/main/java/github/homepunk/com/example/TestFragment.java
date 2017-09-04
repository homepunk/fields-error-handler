package github.homepunk.com.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.homepunk.github.HandleField;

import github.homepunk.com.universalerrorhandler.managers.UniversalHandleManager;

import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.NAME;

/**
 * Created by Homepunk on 04.09.2017.
 **/

public class TestFragment extends Fragment {
    @HandleField(EMAIL)
    EditText testEditText;
    @HandleField(NAME)
    EditText test2EditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login_example, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        testEditText = getActivity().findViewById(R.id.auth_login_email_edit_text);
        test2EditText = getActivity().findViewById(R.id.auth_login_password_edit_text);
        UniversalHandleManager.bind(this);
    }
}
