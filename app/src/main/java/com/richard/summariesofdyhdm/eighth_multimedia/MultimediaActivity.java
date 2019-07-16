package com.richard.summariesofdyhdm.eighth_multimedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.richard.summariesofdyhdm.R;
import com.richard.summariesofdyhdm.eighth_multimedia.notification.MyNotificationActivity;
import com.richard.summariesofdyhdm.eighth_multimedia.photo.CallPhototActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @param
 * @author Richard
 * @time 2019/7/16 18:17
 * @description 多媒体选择页面
 */
public class MultimediaActivity extends AppCompatActivity {

    @BindView(R.id.btn_notification)
    Button btnNotification;
    @BindView(R.id.btn_call_photo)
    Button btnCallPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_notification, R.id.btn_call_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_notification:
                Intent intent = new Intent(this, MyNotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_call_photo:
                Intent intent1 = new Intent(this, CallPhototActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
