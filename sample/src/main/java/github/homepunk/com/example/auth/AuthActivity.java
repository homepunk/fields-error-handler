package github.homepunk.com.example.auth;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import github.homepunk.com.example.R;

public class AuthActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        bind();
        initTabLayout();
        initPagerAdapter();
   }

    private void bind() {
        mButton = findViewById(R.id.activity_auth_button);
        mViewPager = findViewById(R.id.activity_auth_pager);
        mTabLayout = findViewById(R.id.activity_auth_tab_layout);
    }

    private void initPagerAdapter() {
        final PagerAdapter mAuthPagerAdapter = new AuthPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mAuthPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText("Login"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Registration"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(tab.getPosition());
                    mButton.setText(tab.getPosition() == 0 ? "Login" : "Registration");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
