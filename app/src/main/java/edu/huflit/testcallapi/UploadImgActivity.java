package edu.huflit.testcallapi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;

import java.io.IOException;


public class UploadImgActivity extends AppCompatActivity {

    public static final String TAG= UploadImgActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;
    private EditText etUsername,etPassword;
    private ImageView imgFromGallery,imgFromApi;
    private Button btnSelectImage,btnUploadImage;
    private TextView tvUsername,tvPassword;
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG,"onActivityResult");
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data==null){
                            return;
                        }
                        Uri uri = data.getData();
                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imgFromGallery.setImageBitmap(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);
        initUi();
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
    }
    private void initUi(){
        etUsername=findViewById(R.id.et_Username);
        etPassword=findViewById(R.id.et_Password);
        imgFromApi=findViewById(R.id.iv_img_from_api);
        imgFromGallery=findViewById(R.id.iv_img_from_gallery);
        btnSelectImage=findViewById(R.id.btn_choose_img);
        btnUploadImage=findViewById(R.id.btn_upload_img);
        tvUsername=findViewById(R.id.tvUsername);
        tvPassword=findViewById(R.id.tvPassword);
    }
    private void onClickRequestPermission() {
        //version cu thi ko can request
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        //kiemtra xem co quyen READ_EXTERNAL STOREAGE nay chua, ben vis huong dan phien ban cu phai sua lai
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }else {
            String permision [] ={Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permision,MY_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_REQUEST_CODE){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED);{
                openGallery();
            }
        }

    }

    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(i,"select Picture"));
    }


}