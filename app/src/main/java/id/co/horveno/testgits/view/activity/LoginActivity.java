package id.co.horveno.testgits.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.horveno.testgits.R;
import id.co.horveno.testgits.presenter.LoginPresenter;
import id.co.horveno.testgits.utilities.Util;
import id.co.horveno.testgits.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.login_username_field)
    public MaterialEditText username_field;

    @BindView(R.id.login_password_field)
    public MaterialEditText password_field;

    @BindView(R.id.btn_login)
    public Button loginButton;

    @OnClick(R.id.register_button)
    public void toRegisterActivity(View view) {
        if (view.getId() == R.id.register_button)
            startActivity(new Intent(this, RegisterActivity.class));
    }

    LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this, this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_field.getText().toString();
                String password = password_field.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    loginPresenter.requestLogin(username, password);
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.credential_notif), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
