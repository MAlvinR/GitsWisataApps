package id.co.horveno.testgits.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.horveno.testgits.R;
import id.co.horveno.testgits.presenter.RegisterPresenter;
import id.co.horveno.testgits.utilities.DatePickerFragment;
import id.co.horveno.testgits.utilities.Util;
import id.co.horveno.testgits.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView, DatePickerFragment.DateDialogListener {

    @OnClick(R.id.login_button)
    public void toLoginActivity(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @BindView(R.id.register_firstname_field)
    public MaterialEditText firstname_field;
    @BindView(R.id.register_lastname_field)
    public MaterialEditText lastname_field;
    @BindView(R.id.register_username_field)
    public MaterialEditText username_field;
    @BindView(R.id.register_password_field)
    public MaterialEditText password_field;
    @BindView(R.id.register_bdate_field)
    public MaterialEditText bdate_field;
    @BindView(R.id.register_gender_field)
    public MaterialEditText gender_field;
    @BindView(R.id.register_phone_field)
    public MaterialEditText phone_field;

    RegisterPresenter registerPresenter;
    String getBirthDate;
    int bDateClickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenter(this, this);
        ButterKnife.bind(this);

        bdate_field.setFocusableInTouchMode(false);
        bdate_field.setFocusable(false);

        gender_field.setFocusableInTouchMode(false);
        gender_field.setFocusable(false);

    }

    @OnClick(R.id.btn_register)
    public void registerAction(View view) {
        getStringFromEditText();
    }

    @OnClick(R.id.register_bdate_field)
    public void bdateAction(View view) {
        if (bDateClickCount == 0) {
            bdate_field.setFocusableInTouchMode(true);
            bdate_field.setFocusable(true);
            DatePickerFragment pickerDialog = new DatePickerFragment();
            pickerDialog.show(getSupportFragmentManager(), getString(R.string.DATE_DIALOG));
        }
    }

    @OnClick(R.id.register_gender_field)
    public void genderAction(View view) {
        if (bDateClickCount == 0) {
            gender_field.setFocusableInTouchMode(true);
            gender_field.setFocusable(true);

            PopupMenu genderMenu = new PopupMenu(RegisterActivity.this, gender_field);
            genderMenu.getMenuInflater().inflate(R.menu.menu_gender, genderMenu.getMenu());

            genderMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.male:
                            gender_field.setText("Male");
                            return true;
                        case R.id.female:
                            gender_field.setText("Female");
                            return true;
                    }
                    return true;
                }
            });
            genderMenu.show();
        }
    }

    private void getStringFromEditText() {
        String firstname = firstname_field.getText().toString();
        String lastname = lastname_field.getText().toString();
        String username = username_field.getText().toString();
        String password = password_field.getText().toString();
        String bdate = bdate_field.getText().toString();
        String gender = gender_field.getText().toString();
        String phone = phone_field.getText().toString();

        if (!firstname.isEmpty()
                && !lastname.isEmpty()
                && !username.isEmpty()
                && !password.isEmpty()
                && !bdate.isEmpty()
                && !gender.isEmpty()
                && !phone.isEmpty()) {
            registerPresenter.requestRegister(firstname,
                    lastname,
                    username,
                    password,
                    bdate,
                    gender,
                    phone);
        } else {
            Toast.makeText(this, getString(R.string.credential_notif), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishDialog(Date date) {
        getBirthDate = Util.formatDate(date);
        bdate_field.setText(getBirthDate);
    }
}
