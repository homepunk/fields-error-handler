package github.homepunk.com.example.auth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import github.homepunk.com.example.auth.login.LoginFragment;
import github.homepunk.com.example.auth.registration.RegistrationFragment;

/**
 * Created by Homepunk on 14.09.2017.
 **/

public class AuthPagerAdapter extends FragmentStatePagerAdapter {
    private final int mNumberOfTubs;

    public AuthPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.mNumberOfTubs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1: {
                return new RegistrationFragment();
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTubs;
    }
}
