package id.co.horveno.testgits.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import id.co.horveno.testgits.R;
import id.co.horveno.testgits.utilities.SessionManager;
import id.co.horveno.testgits.utilities.Util;

public class SplashActivity extends AppCompatActivity {

    private static final int DELAYED_TIME = 2000;
    private SessionManager sessionManager;
    @BindView(R.id.splash_title)
    public TextView tv_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sessionManager.checkLogin();
                finish();
            }
        }, DELAYED_TIME);
    }
}
