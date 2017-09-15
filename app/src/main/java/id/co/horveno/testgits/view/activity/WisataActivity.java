package id.co.horveno.testgits.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import javax.net.ssl.SSLServerSocket;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.horveno.testgits.R;
import id.co.horveno.testgits.adapter.WisataAdapter;
import id.co.horveno.testgits.presenter.WisataPresenter;
import id.co.horveno.testgits.model.Wisata;
import id.co.horveno.testgits.utilities.DividerItemDecoration;
import id.co.horveno.testgits.utilities.SessionManager;
import id.co.horveno.testgits.view.WisataView;
import ss.com.bannerslider.views.BannerSlider;

import static android.view.View.GONE;

public class WisataActivity extends AppCompatActivity implements WisataView {

    @BindView(R.id.recyclerWisata)
    public RecyclerView listWisata;
    @BindView(R.id.sliderWisata)
    public BannerSlider slider_wisata;


    WisataPresenter wisataPresenter;
    WisataAdapter adapter;
    private String[] mArrSort;
    private int mSort = 0;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        listWisata.setVisibility(GONE);

        mArrSort = new String[]{
                getString(R.string.sort_dataran_tinggi),
                getString(R.string.sort_dataran_rendah),
                getString(R.string.sort_pantai)
        };

        sessionManager = new SessionManager(this);

        wisataPresenter = new WisataPresenter(this, listWisata, this);
        wisataPresenter.getWisata(mArrSort, mSort);
        wisataPresenter.getBanner(slider_wisata, mArrSort, mSort);

        listWisata.setLayoutManager(new GridLayoutManager(this, 2));
        listWisata.addItemDecoration(new DividerItemDecoration(2, dpToPx(5), true));
        listWisata.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        wisataPresenter.getWisata(mArrSort, mSort);
        wisataPresenter.getBanner(slider_wisata, mArrSort, mSort);
    }

    @Override
    public void onTravelResponse(List<Wisata.WisataData> wisataData) {
        adapter = new WisataAdapter(this, wisataData);
        listWisata.setAdapter(adapter);

    }

    @Override
    public void onBannerResponse(List<Wisata.WisataData> wisataData) {

    }

    @Override
    public void updateToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wisata, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            showSortDialog();
            return true;
        } else if (item.getItemId() == R.id.action_add_data) {
            startActivity(new Intent(WisataActivity.this, AddDataActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            sessionManager.logout();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showSortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WisataActivity.this);
        builder.setTitle(R.string.sort_by)
                .setSingleChoiceItems(
                        mArrSort,
                        mSort,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSort = which;
                                wisataPresenter.getWisata(mArrSort, mSort);
                                wisataPresenter.getBanner(slider_wisata, mArrSort, mSort);
                                dialog.dismiss();
                            }
                        }
                );
        builder.create().show();
    }
}
