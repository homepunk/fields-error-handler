# Universal Error Handler

Field validator for Android EditText's which uses both old school programmatic way to set up validated views or annotation processing to generate boilerplate code for you.
# Installation
--------

```groovy
dependencies {
  compile 'com.github.homepunk:universal-error-handler:1.0'
  annotationProcessor 'com.github.homepunk:universal-error-handler-proccessor:1.0'
}
```
# Using old school way
--------
```java
    public class LoginExampleFragment extends Fragment {
    EditText mEmail;
    EditText mPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        bind(root);

        UniversalHandleManager.getFieldsHandleManager(this)
                .target(mEmail, EMAIL)
                .target(mPassword, PASSWORD).validate(target -> target.length() > 1)
                .setHandleListener((targetType, isSuccess) -> {
                    if (!isSuccess) {
                        switch (targetType) {
                            case EMAIL: {
                                showError("Email is not valid");
                                break;
                            }
                            case PASSWORD: {
                                showError("Password can't be empty");
                                break;
                            }
                        }
                    }
                });

        return root;
    }
   }
```
# Using annotations
--------
  - Setup validated field by using `@HandleField` annoation.
  - Setup method that will receive the result of validating the fields using `@OnFieldHandleResult` annonation.
  - Finally Bind class where fields should be proccessed using `UniversalHandleManager.bind(class)` or bind target activity or fragment where your fields places separetly use `UniversalHandleManager.bindTarget(target)` method and for destination class where your recieving method places use `UniversalHandleManager.bindDestination(destination)` method.
  
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
        UniversalHandleManager.bind(this);
        
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
