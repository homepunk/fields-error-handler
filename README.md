# Universal Error Handler

Field validator for Android EditText's which uses both old school programmatic way to set up validated views or annotation processing to generate boilerplate code for you.

# Using annotations
  - Setup validated field by using `@HandleField` annoation.
  - Setup method that will receive the result of validating the fields using `@OnFieldHandleResult` annonation.
  - Bind target activity or fragment where your fields places using `UersalHandleManager.target(target)` method.
  - Finally bind destination class where your recieving method places using `UersalHandleManager.destination(destination)` method.
  
   ```java
  public class RegistrationExampleFragment extends Fragment {
    @HandleField(EMAIL)
    EditText mEmail;
    @HandleField(value = PASSWORD, action = ON_TEXT_CHANGE)
    EditText mPassword;
    @HandleField(PASSWORD_CONFIRMATION)
    EditText mPasswordConfirmation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, container, false);
        bind(root);
        UniversalHandleManager.target(this);
        UniversalHandleManager.destination(this);
        
        return root;
    }

    @OnFieldHandleResult("RegistrationFragment")
    public void onFieldsHandleResult(int fieldType, boolean isSuccess) {
        switch (fieldType) {
            case EMAIL: {
                showError("Email not correct");
                break;
            }
            case PASSWORD: {
                showError("Password can't be empty");
                break;
            }
            case PASSWORD_CONFIRMATION: {
                showError("Password don't match");
                break;
            }
        }
    }
```
