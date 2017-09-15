package id.co.horveno.testgits.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.horveno.testgits.R;
import id.co.horveno.testgits.model.BannerData;
import id.co.horveno.testgits.utilities.Constant;
import id.co.horveno.testgits.view.WisataView;
import id.co.horveno.testgits.model.Wisata;
import id.co.horveno.testgits.network.ApiService;
import id.co.horveno.testgits.view.activity.DetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by ASUS on 08/09/2017.
 */

public class WisataPresenter {
    private WisataView wisataView;
    private RecyclerView recyclerView;
    private Context context;

    public WisataPresenter(WisataView wisataView, RecyclerView recyclerView, Context context) {
        this.wisataView = wisataView;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void getWisata(String[] mArrSort, int mSort) {
        int sortMode = 0;
        if (mArrSort[mSort].equals(context.getString(R.string.sort_dataran_tinggi))) {
            sortMode = 1;
            wisataView.updateToolbarTitle(context.getString(R.string.sort_dataran_tinggi));
        } else if (mArrSort[mSort].equals(context.getString(R.string.sort_dataran_rendah))) {
            sortMode = 2;
            wisataView.updateToolbarTitle(context.getString(R.string.sort_dataran_rendah));
        } else if (mArrSort[mSort].equals(context.getString(R.string.sort_pantai))) {
            sortMode = 3;
            wisataView.updateToolbarTitle(context.getString(R.string.sort_pantai));
        }

        final ProgressDialog dialog = new ProgressDialog((Context) wisataView);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setMessage(((Context) wisataView).getString(R.string.please_wait));
        dialog.show();

        ApiService.getInstance()
                .getApiInterface()
                .getTravelList(sortMode)
                .enqueue(new Callback<Wisata>() {
                    @Override
                    public void onResponse(Call<Wisata> call, Response<Wisata> response) {
                        if (response.isSuccessful()) {
                            wisataView.onTravelResponse(response.body().wisataData);
                            recyclerView.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Wisata> call, Throwable t) {
                        t.getMessage();
                    }
                });
    }

    public void getBanner(final BannerSlider bannerSlider, String[] mArrSort, int mSort) {
        int sortMode = 0;
        if (mArrSort[mSort].equals(context.getString(R.string.sort_dataran_tinggi))) {
            sortMode = 1;
        } else if (mArrSort[mSort].equals(context.getString(R.string.sort_dataran_rendah))) {
            sortMode = 2;
        } else if (mArrSort[mSort].equals(context.getString(R.string.sort_pantai))) {
            sortMode = 3;
        }

        ApiService.getInstance()
                .getApiInterface()
                .getTravelList(sortMode)
                .enqueue(new Callback<Wisata>() {
                    @Override
                    public void onResponse(Call<Wisata> call, final Response<Wisata> response) {
                        List<Banner> banners = new ArrayList<>();
                        final BannerData bannerData = new BannerData();

                        banners.clear();
                        bannerData.clear();
                        bannerSlider.removeAllBanners();

                        for (int i = 0; i < 5; i++) {
                            bannerData.addData(
                                    response.body().wisataData.get(i).id_wisata,
                                    response.body().wisataData.get(i).gambar_wisata,
                                    response.body().wisataData.get(i).judul_wisata,
                                    response.body().wisataData.get(i).desc_wisata
                            );

                            banners.add(new RemoteBanner(Constant.IMG_URL + response.body().wisataData.get(i).gambar_wisata));
                        }

                        bannerSlider.setBanners(banners);
                        bannerSlider.setMustAnimateIndicators(true);
                        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
                            @Override
                            public void onClick(int position) {

                                Intent toDetail = new Intent(context, DetailActivity.class);
                                toDetail.putExtra("WISATA_ID", response.body().wisataData.get(position).id_wisata);
                                context.startActivity(toDetail);

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Wisata> call, Throwable t) {
                        t.getMessage();
                    }
                });
    }
}
