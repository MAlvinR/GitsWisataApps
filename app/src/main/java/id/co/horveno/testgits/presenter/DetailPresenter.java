package id.co.horveno.testgits.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import id.co.horveno.testgits.R;
import id.co.horveno.testgits.model.Detail;
import id.co.horveno.testgits.network.ApiService;
import id.co.horveno.testgits.view.DetailView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {
    private DetailView detailView;
    private Context context;

    public DetailPresenter(DetailView detailView, Context context) {
        this.detailView = detailView;
        this.context = context;
    }

    public void getDetail(int itemId) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.please_wait));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        ApiService
                .getInstance()
                .getApiInterface()
                .getDetail(itemId)
                .enqueue(new Callback<Detail>() {
                    @Override
                    public void onResponse(Call<Detail> call, Response<Detail> response) {
                        boolean status = response.body().statusDetail;
                        Log.d("onResponse_Detail", "" + status);
                        if (status) {
                            dialog.dismiss();
                            detailView.onDetailResponse(response.body());
                        } else {
                            dialog.dismiss();
                            detailView.onErrorResponse(response.body().msgDetail);
                        }
                    }

                    @Override
                    public void onFailure(Call<Detail> call, Throwable t) {
                        Log.d("onFailure_Detail", t.getMessage());
                    }
                });
    }
}
