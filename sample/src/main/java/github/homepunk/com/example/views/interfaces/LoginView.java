package github.homepunk.com.example.views.interfaces;

import android.app.Activity;
import android.content.Context;

/**
 * Created by homepunk on 25.08.17.
 */

public interface LoginView {
    void onFieldsHandleResult(int fieldType);

    void showError(String message);

    Context getContext();

    Activity getActivity();
}
