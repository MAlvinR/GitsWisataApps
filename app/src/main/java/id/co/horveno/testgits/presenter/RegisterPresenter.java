package id.co.horveno.testgits.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import id.co.horveno.testgits.R;
import id.co.horveno.testgits.model.Register;
import id.co.horveno.testgits.network.ApiService;
import id.co.horveno.testgits.utilities.Util;
import id.co.horveno.testgits.view.RegisterView;
import id.co.horveno.testgits.view.activity.LoginActivity;
import id.co.horveno.testgits.view.activity.RegisterActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {
    private RegisterView mRegisterView;
    private Context mContext;

    public RegisterPresenter(RegisterView registerView, Context context) {
        this.mRegisterView = registerView;
        this.mContext = context;
    }

    public void requestRegister(final String firstname,
                                final String lastname,
                                final String username,
                                final String password,
                                final String birthdate,
                                final String gender,
                                final String phone) {

        ApiService.getInstance()
                .getApiInterface()
                .getRegister(firstname, lastname, username, password, birthdate, gender, phone)
                .enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if (response.isSuccessful()) {
                            Log.d("onFailure_Register", username + "\n" + password);
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            mRegisterView.onSuccessResponse(mContext.getString(R.string.register_success));
                        } else {
                            mRegisterView.onErrorResponse(response.body().msgRegister);
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        Log.d("onFailure_Register", t.getMessage());
                    }
                });
    }
}
