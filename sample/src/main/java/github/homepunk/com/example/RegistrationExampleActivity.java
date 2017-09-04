package github.homepunk.com.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.homepunk.github.HandleField;

import github.homepunk.com.universalerrorhandler.managers.UniversalHandleManager;

import static com.homepunk.github.models.UniversalFieldAction.ON_FOCUS_MISS;
import static com.homepunk.github.models.UniversalFieldType.EMAIL;
import static com.homepunk.github.models.UniversalFieldType.PASSWORD;

/**
 * A login screen that offers registration via email/password.
 */
public class RegistrationExampleActivity extends AppCompatActivity {
    @HandleField(value = EMAIL, action = ON_FOCUS_MISS)
    AutoCompleteTextView mEmailView;
    @HandleField(PASSWORD)
    EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_example);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        UniversalHandleManager.bind(this);
    }
}

