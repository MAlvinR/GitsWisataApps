package id.co.horveno.testgits.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wisata {
    @SerializedName("status")
    public String status_wisata;
    @SerializedName("status_code")
    public String kode_status_wisata;
    @SerializedName("message")
    public String msg_wisata;
    @SerializedName("data")
    public List<WisataData> wisataData;

    public class WisataData {
        @SerializedName("id_data")
        public int id_wisata;
        @SerializedName("title")
        public String judul_wisata;
        @SerializedName("url_image")
        public String gambar_wisata;
        @SerializedName("rate")
        public String rating_wisata;
        @SerializedName("description")
        public String desc_wisata;
    }
}
