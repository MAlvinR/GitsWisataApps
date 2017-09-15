package id.co.horveno.testgits.view;

import id.co.horveno.testgits.model.Detail;

public interface DetailView {
    void onDetailResponse(Detail detailData);
    void onErrorResponse(String message);
}
