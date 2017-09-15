package id.co.horveno.testgits.view.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.horveno.testgits.R;
import id.co.horveno.testgits.model.Detail;
import id.co.horveno.testgits.presenter.DetailPresenter;
import id.co.horveno.testgits.utilities.Constant;
import id.co.horveno.testgits.utilities.Util;
import id.co.horveno.testgits.view.DetailView;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @BindView(R.id.detail_header_thumb)
    ImageView detailHeaderThumb;
    @BindView(R.id.detailJudul)
    TextView detailJudul;
    @BindView(R.id.detailDescription)
    TextView detailDescription;
    @BindView(R.id.ratingDetail)
    RatingBar ratingBar;
    @BindView(R.id.kategoriDetail)
    TextView kategoriDetail;
    @BindView(R.id.tanggalDetail)
    TextView tanggalDetail;
    @BindView(R.id.textRatingDetail)
    TextView txtRating;
    @BindView(R.id.lokasiDetail)
    TextView lokasiDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbarDetail;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingDetail;
    @BindView(R.id.app_bar)
    AppBarLayout appBarDetail;

    private Integer idWisata;
    DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idWisata = getIntent().getIntExtra("WISATA_ID", 0);
        detailPresenter = new DetailPresenter(this, this);
        detailPresenter.getDetail(idWisata);
    }

    @Override
    public void onDetailResponse(Detail detailData) {

        detailJudul.setText(detailData.detailData.get(0).titleDetail);
        initCollapsingToolbar(detailData.detailData.get(0).titleDetail);
        detailDescription.setText(detailData.detailData.get(0).descDetail);
        /*Rating Wisata*/
        ratingBar.setRating(detailData.detailData.get(0).rateDetail);
        txtRating.setText(String.valueOf(detailData.detailData.get(0).rateDetail));

        kategoriDetail.setText(detailData.detailData.get(0).categoryDetail);
        lokasiDetail.setText("Lokasi : " + detailData.detailData.get(0).locationDetail);

        tanggalDetail.setText(detailData.detailData.get(0).dateDetail);

        final String IMG_PATH = detailData.detailData.get(0).urlThumbDetail;

        Picasso.with(this)
                .load(Constant.IMG_URL + IMG_PATH)
                .placeholder(R.drawable.mountplaceholder)
                .into(detailHeaderThumb);
    }

    @Override
    public void onErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.i("Detail_Status", message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initCollapsingToolbar(final String title) {
        collapsingDetail.setTitle("");
        appBarDetail.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarDetail.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingDetail.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    collapsingDetail.setTitle("");
                    isShow = false;
                }

            }
        });

    }
}
