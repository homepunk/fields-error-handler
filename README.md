Fields Error Handler :v:
--------

Field validator for Android EditText's which uses both old school programmatic way to set up validated views or annotation processing to generate boilerplate code for you.

#### Supported target types:
* _`NAME`_
* _`EMAIL`_
* _`PHONE`_
* _`CREDIT_CARD`_
* _`PASSWORD`_
* _`PASSWORD_CONFIRMATION`_

#### Supported target actions: 
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
    EditText mEmail;
    EditText mPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        bind(root);

       UniversalHandleManager.getFieldsHandleManager(this)
                .target(mName, NAME, ON_FOCUS_MISS)
                .target(mPhone, PHONE, FieldHandler.builder()
                        .appendError(201, R.string.error_phone_required, target -> target.length() > 8)
                        .setMask("+(###)##-##-###", '#')
                        .build(), ON_FOCUS, ON_FOCUS_MISS, ON_TEXT_CHANGE)
                .target(mEmail, EMAIL, FieldHandler.builder()
                        .appendError(202, R.string.error_field_required, String::isEmpty)
                        .appendError(203, R.string.error_email_contain_spaces, target -> target.contains(" "))
                        .build())
                .target(mPassword, PASSWORD)
                .setHandleListener(handleResult -> {
                    if (!handleResult.isSuccess()) {
                        showError(handleResult.getMessage());
                    }
                });

        return root;
    }
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
### Custom validation conditions
In order to make your own validator you should simply create class implementing `UniversalValidator` and setup logic in `isValid(String target)` method where `target` is a text to validate
```java
public class ExampleValidator implements UniversalValidator {
    @Override
    public boolean isValid(String target) {
        return false;
    }
}
```
------

# Response errors
------
In progress
