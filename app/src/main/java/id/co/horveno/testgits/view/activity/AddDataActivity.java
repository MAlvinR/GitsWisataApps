package id.co.horveno.testgits.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import id.co.horveno.testgits.utilities.Constant;

import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.horveno.testgits.R;
import id.co.horveno.testgits.utilities.SessionManager;

public class AddDataActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 123;
    @BindView(R.id.uploadToolbar)
    public Toolbar uploadToolbar;
    @BindView(R.id.img_upload)
    public ImageView imgUpload;
    @BindView(R.id.add_title_field)
    MaterialEditText addTitleField;
    @BindView(R.id.add_category_field)
    MaterialEditText addCategoryField;
    @BindView(R.id.add_location_field)
    MaterialEditText addLocationField;
    @BindView(R.id.add_description_field)
    MaterialEditText addDescriptionField;
    @BindView(R.id.btnUpload)
    Button btn_upload;
    private Uri filePath;
    Bitmap bitmap;
    SessionManager sessionManager;
    private String idUser;
    private String namaWisata;
    private String kategoriWisata;
    private String lokasiWisata;
    private String deskripsiWisata;

    int categoryClickCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        ButterKnife.bind(this);

        setSupportActionBar(uploadToolbar);
        getSupportActionBar().setTitle("Upload New Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addCategoryField.setFocusable(false);
        addCategoryField.setFocusableInTouchMode(false);

        btn_upload.setEnabled(false);

        sessionManager = new SessionManager(this);
        idUser = sessionManager.getIdUser();

        btn_upload.setOnClickListener(this);
        imgUpload.setOnClickListener(this);

        requestStoragePermission();

    }

    /*
    * This is the method responsible for image Upload
    * We need the full image path and the name for the image in this method
    * */
    public void uploadMultipart() {
        //getting name for the image
        //getting the actual path of the image
        String path = getPath(filePath);
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();
            /*Get text from edit text*/
            namaWisata = addTitleField.getText().toString();
            kategoriWisata = addCategoryField.getText().toString();
            lokasiWisata = addLocationField.getText().toString();
            deskripsiWisata = addDescriptionField.getText().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constant.IMG_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("judul", namaWisata)
                    .addParameter("location", lokasiWisata)
                    .addParameter("kategori", kategoriWisata)
                    .addParameter("deskripsi", deskripsiWisata)
                    .addParameter("id_user", idUser)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    //Starting the Upload
                    .startUpload();

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                imgUpload.setBackgroundDrawable(ob);
                imgUpload.setImageResource(0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == PERMISSION_REQUEST_STORAGE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //If permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.add_category_field)
    public void chooseCategory(View view) {

    if (categoryClickCount == 0) {
        addCategoryField.setFocusable(true);
        addCategoryField.setFocusableInTouchMode(true);

        PopupMenu categorymenu = new PopupMenu(AddDataActivity.this, addCategoryField);
        categorymenu.getMenuInflater().inflate(R.menu.menu_kategori, categorymenu.getMenu());

        categorymenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.kat_datTinggi:
                        addCategoryField.setText(getString(R.string.sort_dataran_tinggi));
                        return true;
                    case R.id.kat_datRendah:
                        addCategoryField.setText(getString(R.string.sort_dataran_rendah));
                        return true;
                    case R.id.kat_pantai:
                        addCategoryField.setText(getString(R.string.sort_pantai));
                        return true;
                }
                return true;
            }
        });
        categorymenu.show();
    }


    }


    @Override
    public void onClick(View v) {
        if (v == imgUpload) {
            showFileChooser();
            btn_upload.setEnabled(true);
        }
        if (v == btn_upload) {
            uploadMultipart();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
