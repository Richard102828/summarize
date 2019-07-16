package com.richard.summariesofdyhdm.eighth_multimedia.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.richard.summariesofdyhdm.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @param
 * @author Richard
 * @time 2019/7/16 19:54
 * @description 两个按钮进行调用手机相机、相册
 */
public class CallPhototActivity extends AppCompatActivity {

    @BindView(R.id.btn_call_the_camera)
    Button    btnCallTheCamera;
    @BindView(R.id.btn_call_the_photo)
    Button    btnCallThePhoto;
    @BindView(R.id.img_photo)
    ImageView imgPhoto;

    private static final int TAKE_PHOTO = 1;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_photot);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_call_the_camera, R.id.btn_call_the_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_call_the_camera:
                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(this, "com.richard" +
                            ".summariesofdyhdm.eighth_multimedia.photo.fileprovider",outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机
                Intent intent = new Intent("android.media.action.IMAGE GAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
                break;
            case R.id.btn_call_the_photo:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().
                                openInputStream(imageUri));
                        imgPhoto.setImageBitmap(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
                default:
                    break;
        }
    }

}
