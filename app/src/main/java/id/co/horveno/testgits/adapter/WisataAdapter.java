package id.co.horveno.testgits.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.horveno.testgits.R;
import id.co.horveno.testgits.model.Wisata;
import id.co.horveno.testgits.utilities.Constant;
import id.co.horveno.testgits.utilities.Util;
import id.co.horveno.testgits.view.activity.DetailActivity;
import id.co.horveno.testgits.view.activity.WisataActivity;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder> {

    List<Wisata.WisataData> wisataData;
    Context mContext;

    public WisataAdapter(Context context, List<Wisata.WisataData> wisataData) {
        this.wisataData = wisataData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(mContext).inflate(R.layout.item_grid_wisata, parent, false);
        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Wisata.WisataData data = wisataData.get(position);

        Log.d("ID_Wisata", "" + data.id_wisata);

        Picasso.with(mContext)
                .load(Constant.IMG_URL + data.gambar_wisata)
                .placeholder(R.drawable.mountplaceholder)
                .into(holder.gambarWisata);

        holder.judulWisata.setText(data.judul_wisata);

        holder.cardViewGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDetail = new Intent(mContext, DetailActivity.class);
                toDetail.putExtra("WISATA_ID", data.id_wisata);
                Log.d("Debug_WisataID", "" + data.id_wisata);
                mContext.startActivity(toDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wisataData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_wisata)
        public ImageView gambarWisata;
        @BindView(R.id.judul_wisata)
        public TextView judulWisata;
        @BindView(R.id.cardViewGrid)
        public CardView cardViewGrid;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Util.PtSansRegular(mContext, judulWisata);
        }
    }
}
