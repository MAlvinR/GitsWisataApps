package id.co.horveno.testgits.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login {
    @SerializedName("status")
    public String statusLogin;

    @SerializedName("status_code")
    public String statusCodeLogin;

    @SerializedName("message")
    public String msgLogin;

    @SerializedName("data")
    public List<LoginData> loginData;

    public class LoginData {
        @SerializedName("id_user")
        public String idUserLogin;

        @SerializedName("username")
        public String usernameLogin;

        @SerializedName("password")
        public String passwordLogin;
    }
}

