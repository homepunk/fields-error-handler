package github.homepunk.com.example.interfaces;

import android.app.Activity;
import android.content.Context;

/**
 * Created by homepunk on 25.08.17.
 */

public interface LoginExampleView {
    void showError(String message);

    Context getContext();

    Activity getActivity();
}
