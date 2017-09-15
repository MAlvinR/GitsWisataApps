package id.co.horveno.testgits.view;

import java.util.List;

import id.co.horveno.testgits.model.Wisata;

public interface WisataView {
    void onTravelResponse(List<Wisata.WisataData> wisataData);
    void onBannerResponse(List<Wisata.WisataData> wisataData);
    void updateToolbarTitle(String title);
}
