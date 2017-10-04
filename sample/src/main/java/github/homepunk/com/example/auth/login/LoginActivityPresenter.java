package github.homepunk.com.example.auth.login;

import github.homepunk.com.example.auth.login.interfaces.LoginPresenter;
import github.homepunk.com.example.auth.login.interfaces.LoginView;

/**
 * Created by Homepunk on 28.09.2017.
 **/

public class LoginActivityPresenter implements LoginPresenter {
    private LoginView view;

    @Override
    public void init(LoginView view) {
        this.view = view;
    }

    @Override
    public void terminate() {
        this.view = null;
    }
}
