package com.richard.summariesofdyhdm.seventh_contentprovider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.richard.summariesofdyhdm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @param
 * @author Richard
 * @time 2019/7/16 16:27
 * @description ContentProvider的页面
 */
public class ContentProviderActivity extends AppCompatActivity {

    @BindView(R.id.btn_call)
    Button btnCall;
    @BindView(R.id.btn_reade_phone_contacts)
    Button btnReadePhoneContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_call, R.id.btn_reade_phone_contacts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_call:
                if (ContextCompat.checkSelfPermission(ContentProviderActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ContentProviderActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        call();
                    }
                }
                break;
            case R.id.btn_reade_phone_contacts:
                Intent intent = new Intent(this,ReadPhoneContactsActivity.class);
                startActivity(intent);
                break;
        }
    }


    //打电话方法
    @SuppressLint("MissingPermission")
    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    call();
                    }
                }else {
                    Toast.makeText(this,"你没有授权",Toast.LENGTH_SHORT).show();
                }
                    break;
                    default:
                    break;
            }
        }

    }
