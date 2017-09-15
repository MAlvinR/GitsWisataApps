package id.co.horveno.testgits.network;

import id.co.horveno.testgits.model.Detail;
import id.co.horveno.testgits.model.Register;
import id.co.horveno.testgits.model.Upload;
import id.co.horveno.testgits.model.Wisata;
import id.co.horveno.testgits.utilities.Constant;
import id.co.horveno.testgits.model.Login;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("post/user/login")
    Call<Login> getLogin(@Field("username") String username,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("post/user/account")
    Call<Register> getRegister(@Field("first_name") String firstname,
                               @Field("last_name") String lastname,
                               @Field("username") String username,
                               @Field("password") String password,
                               @Field("bdate") String birthdate,
                               @Field("gender") String gender,
                               @Field("phone") String numberPhone);

    @GET("get/filter/dataalam")
    Call<Wisata> getTravelList(
            @Query("kategori") int id);

    @GET("get/detil/dataalam")
    Call<Detail> getDetail(
            @Query("itemid") int id);

    @Multipart
    @POST("post/data/upload")
    Call<Upload> uploadWisataBaru(
            @Part("judul") RequestBody judul,
            @Part("location") RequestBody location,
            @Part("kategori") RequestBody kategori,
            @Part("deskripsi") RequestBody deskripsi,
            @Part("id_user") RequestBody idUser,
            @Part MultipartBody.Part image);

}
