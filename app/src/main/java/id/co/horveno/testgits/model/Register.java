package id.co.horveno.testgits.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Register {
    @SerializedName("status")
    public String statusRegister;

    @SerializedName("status_code")
    public String statusCodeRegister;

    @SerializedName("message")
    public String msgRegister;

    @SerializedName("data")
    public List<RegisterData> registerData;

    public class RegisterData {
        @SerializedName("first_name")
        public String firstnameRegister;

        @SerializedName("last_name")
        public String lastnameRegister;

        @SerializedName("username")
        public String usernameRegister;

        @SerializedName("password")
        public String passwordRegister;

        @SerializedName("bdate")
        public String birthdateRegister;

        @SerializedName("gender")
        public String genderRegister;

        @SerializedName("phone")
        public String numberphoneRegister;
    }
}
