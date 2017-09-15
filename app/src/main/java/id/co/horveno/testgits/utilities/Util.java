package id.co.horveno.testgits.utilities;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Base64;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    /*public static boolean EmptyField(EditText e) {
        if (e.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }*/

    public static String formatDate(Date date) {
        final String DATE_FORMAT = "dd-MM-yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String hireDate = dateFormat.format(date);
        return hireDate;
    }

    public static void PtSansRegular(Context context, TextView resId) {
        Typeface ptSansRegular = Typeface.createFromAsset(context.getAssets(), "fonts/ptsans_regular.ttf");
        resId.setTypeface(ptSansRegular);
    }
}
