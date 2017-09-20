Universal Error Handler :v:
==================
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
### Using annotations
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
### Custom validation conditions
------
In order to make your own validator you should simply create class implementing `UniversalValidator` and setup logic in `isValid(String target)` method where `target` is a text to validate
```java
public class ExampleValidator implements UniversalValidator {
    @Override
    public boolean isValid(String target) {
        return false;
    }
}
```
