package github.homepunk.com.example.interfaces;

import android.app.Activity;
import android.content.Context;

import github.homepunk.com.universalerrorhandler.handlers.interfaces.listeners.HandleFailListener;

/**
 * Created by homepunk on 25.08.17.
 */

public interface LoginExampleView extends HandleFailListener {
    void showError(String message);

    Context getContext();

    Activity getActivity();
}
