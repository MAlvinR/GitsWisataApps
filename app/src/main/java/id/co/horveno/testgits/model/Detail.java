package id.co.horveno.testgits.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Detail {
    @SerializedName("status")
    public Boolean statusDetail;
    @SerializedName("status_code")
    public int statusCodeDetail;
    @SerializedName("message")
    public String msgDetail;

    @SerializedName("data")
    public List<DetailData> detailData;

    public class DetailData {
        @SerializedName("id_data")
        public int id_detail;
        @SerializedName("title")
        public String titleDetail;
        @SerializedName("description")
        public String descDetail;
        @SerializedName("url_image")
        public String urlThumbDetail;
        @SerializedName("created_at")
        public String dateDetail;
        @SerializedName("category")
        public String categoryDetail;
        @SerializedName("Location")
        public String locationDetail;
        @SerializedName("rate")
        public float rateDetail;


    }
}
