package id.co.horveno.testgits.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import id.co.horveno.testgits.view.activity.LoginActivity;
import id.co.horveno.testgits.view.activity.WisataActivity;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "LOGIN_PREFERENCES";
    private static final String is_login = "islogin";
    public static final String keyusername = "keyUsername";
    public static final String keyIdUser = "id_user";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSessionByUsername(String username){
        editor.putBoolean(is_login, true);
        editor.putString(keyusername, username);
        editor.commit();
    }

    public String getIdUser() {
        return pref.getString(keyIdUser, "");
    }

    public void setIdUser(String idUser) {
        editor.putString(keyIdUser, idUser);
        editor.commit();
    }

    public void checkLogin(){
        if (!this.is_login()){
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            Intent i = new Intent(context, WisataActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /*public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(keyusername, pref.getString(keyusername, null));
        return user;
    }*/

}
