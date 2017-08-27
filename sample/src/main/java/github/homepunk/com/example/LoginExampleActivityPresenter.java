package github.homepunk.com.example;


import com.squareup.okhttp.internal.http.RealResponseBody;

import github.homepunk.com.example.interfaces.LoginExamplePresenter;
import github.homepunk.com.example.interfaces.LoginExampleView;
import github.homepunk.com.universalerrorhandler.ErrorHandleManager;
import io.reactivex.functions.Action;

/**
 * Created by homepunk on 25.08.17.
 */

class LoginExampleActivityPresenter implements LoginExamplePresenter {
    private LoginExampleView view;

    @Override
    public void bind(LoginExampleView view) {
        this.view = view;
    }

    @Override
    public void terminate() {
        this.view = null;
    }

    @Override
    public void onLoginClick() {
        RealResponseBody responseBody = new RealResponseBody(null, null);
        ErrorHandleManager.target(responseBody).doOnHandleFail(new Action() {
            @Override
            public void run() throws Exception {

            }
        });
    }
}
