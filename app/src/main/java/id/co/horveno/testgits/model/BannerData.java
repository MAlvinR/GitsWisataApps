package id.co.horveno.testgits.model;

import java.util.ArrayList;

public class BannerData {

    private ArrayList<Integer> id_banner = new ArrayList<>();
    private ArrayList<String> title_banner = new ArrayList<>();
    private ArrayList<String> description_banner = new ArrayList<>();
    private ArrayList<String> url_img_banner = new ArrayList<>();

    public void clear() {
        id_banner.clear();
        title_banner.clear();
        description_banner.clear();
        url_img_banner.clear();
    }

    void addId_Data(Integer id_data) {
        this.id_banner.add(id_data);
    }

    void addTitle(String title) {
        this.title_banner.add(title);
    }

    void addDescription(String description) {
        this.description_banner.add(description);
    }

    void addUrl_Image(String url_image) {
        this.url_img_banner.add(url_image);
    }

    public Integer getId_Data(Integer position) {
        return id_banner.get(position);
    }

    public void addData(Integer id_data, String title, String description, String url_image) {
        addId_Data(id_data);
        addTitle(title);
        addDescription(description);
        addUrl_Image(url_image);
    }

    public String getTitle(Integer position) {
        return title_banner.get(position);
    }

    public String getDescription(Integer position) {
        return description_banner.get(position);
    }

    public String url_image(Integer position) {
        return url_img_banner.get(position);
    }

}
