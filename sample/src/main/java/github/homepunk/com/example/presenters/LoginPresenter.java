package github.homepunk.com.example.presenters;

import github.homepunk.com.example.views.interfaces.LoginView;

/**
 * Created by homepunk on 25.08.17.
 */

public interface LoginPresenter {
    void bind(LoginView view);

    void terminate();

    void onLoginClick();
}
