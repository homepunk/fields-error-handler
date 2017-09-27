Fields Error Handler :v:
--------

Field validator for Android EditText's which uses both old school programmatic way to set up validated views or annotation processing to generate boilerplate code for you.

#### Features
* Validate inputs based on target type;
* Supports multiple actions when fields would be validated;
* Custom handling error conditions by field
* Pre-defined default error messages translated into English;

##### Supported target types:
* _`NAME`_
* _`EMAIL`_
* _`PHONE`_
* _`PASSWORD`_
* _`CREDIT_CARD`_

##### Supported target actions: 
- _`ON_CLICK`_
- _`ON_FOCUS`_
- _`ON_FOCUS_MISS`_
- _`ON_TEXT_CHANGE`_
- _`AFTER_TEXT_CHANGE`_
- _`BEFORE_TEXT_CHANGE`_

Validation conditions use default patterns from the android library and can be simply replaced by setuping your custom validating class

## Installation
--------

```groovy
in progress
```

## Using old school way
```java
    public class LoginExampleFragment extends Fragment {
    private EditText mEmail;
    private EditText mPassword;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        bind(root);

         FieldsHandleManager.getInstance(this)
                .target(mEmail, EMAIL)
                .target(mPassword, PASSWORD, ON_TEXT_CHANGE, ON_FOCUS_MISS)
                .setOnHandleResultListener(result -> {
                    if (!result.isSuccess()) {
                        showError(result.getMessage());
                    }
                });
        return root;
    }
   }
```

### Custom handle conditions
You can simply setup field handler using TargetHandler builder

#### Features: 
* Supports adding your custom particular errors using add(int code, @StringRes int stringResId, ParticularHandler handler) method.
* Supports removing already setted up errors using remove(int code) method. 
* Supports references to string resources ids

```java
public class RegistrationFragment extends Fragment {
    public static final int PASSWORD_VERIFICATION = 1;
    public static final int PASSWORDS_DONT_MATCH = 2;
    ...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ...
        FieldsHandleManager.getInstance(this)
                .target(mPassword, PASSWORD)
                .target(mPasswordVerification, PASSWORD_VERIFICATION, TargetHandler.getBuilder()
                        .add(PASSWORDS_DONT_MATCH, "Passwords don't match", target -> target.equals(mPassword.getText().toString()))
                        .remove(TEXT_CONTAINS_UPPER_CASE)
                        .build());
        ...                
    }
```

## Using annotations
  - Setup validated field by using `@HandleField` annoation (Default action is _`ON_FOCUS_MISS`_)
  - Setup method that will receive the result of validating the fields using `@OnFieldHandleResult` annonation.
  - Finally Bind class where fields should be proccessed using `UniversalHandleManager.bind(class)` or bind target activity or fragment where your fields places separetly use `UniversalHandleManager.bindTarget(target)` method and for destination class where your recieving method places use `UniversalHandleManager.bindDestination(destination)` method.
  
   ```java
  public class RegistrationExampleFragment extends Fragment {
    @HandleField(EMAIL)
    EditText mEmail;
    @HandleField(value = PASSWORD, action = ON_TEXT_CHANGE)
    EditText mPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, container, false);
        bind(root);
        UniversalHandleManager.bind(this);
        
        return root;
    }

    @OnFieldHandleResult("RegistrationExampleFragment")
    public void onFieldsHandleResult(int fieldType, boolean isSuccess) {
        if (!isSuccess) {
            switch (fieldType) {
                case EMAIL: {
                    showError("Email not correct");
                    break;
                }
                case PASSWORD: {
                    showError("Password can't be empty");
                    break;
                }
            }
        }
     }
```
