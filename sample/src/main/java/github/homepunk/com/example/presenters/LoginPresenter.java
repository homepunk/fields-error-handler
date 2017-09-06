package github.homepunk.com.example.interfaces;

/**
 * Created by homepunk on 25.08.17.
 */

public interface LoginExamplePresenter {
    void bind(LoginExampleView view);

    void terminate();

    void onLoginClick();
}
