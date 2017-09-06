package github.homepunk.com.example.presenters;


import com.homepunk.github.OnFieldHandleResult;
import com.squareup.okhttp.internal.http.RealResponseBody;

import github.homepunk.com.example.views.interfaces.LoginView;
import github.homepunk.com.universalerrorhandler.managers.UniversalHandleManager;

/**
 * Created by homepunk on 25.08.17.
 */

public class LoginActivityPresenter implements LoginPresenter {
    private LoginView view;

    public LoginActivityPresenter() {
        UniversalHandleManager.setHandleResultDestination(this);
    }

    @Override

    public void bind(LoginView view) {
        this.view = view;
    }

    @Override
    public void terminate() {
        this.view = null;
    }

    @Override
    public void onLoginClick() {
        RealResponseBody responseBody = new RealResponseBody(null, null);
    }

    @OnFieldHandleResult("LoginActivity")
    public void onFieldHandleResult(int errorType) {
        view.onFieldsHandleResult(errorType);
    }
}
