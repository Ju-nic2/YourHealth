package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

public class chooseActivity extends FragmentActivity implements View.OnClickListener {
    TextView gotoAlbum;
    TextView setDefault;
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;
    private Boolean isPermission = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        tedPermission();//갤러리 권한 설정

        gotoAlbum = findViewById(R.id.getAlbum);
        gotoAlbum.setOnClickListener(this);

        setDefault = findViewById(R.id.setdefault);
        setDefault.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == gotoAlbum){

           if(isPermission) goToAlbum();
        }else if(view == setDefault ) goToDefault();

    }
    private void goToDefault(){
        Intent intent = new Intent();

        setResult(RESULT_CANCELED,intent);
        finish();

    }
    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();

            Cursor cursor = null;

            try {
                Intent intent = new Intent();
                intent.setData(photoUri);
                setResult(RESULT_OK,intent);
                finish();


            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

   private void showToast(String message) {
       Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
       toast.show();
   }
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }
}