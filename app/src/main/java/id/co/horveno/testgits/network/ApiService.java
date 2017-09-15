package id.co.horveno.testgits.network;

import java.util.concurrent.TimeUnit;

import id.co.horveno.testgits.BuildConfig;
import id.co.horveno.testgits.utilities.Constant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    private static ApiService apiService;
    private ApiInterface apiInterface;

    Retrofit retrofit;

    private ApiService() {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient().newBuilder();
        okhttpBuilder.connectTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.writeTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.readTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okhttpBuilder.addInterceptor(interceptor);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okhttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiService getInstance() {
        if (apiService == null) {
            apiService = new ApiService();
        }

        return apiService;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }
}
