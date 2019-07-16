package com.richard.summariesofdyhdm.eighth_multimedia.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.richard.summariesofdyhdm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @param
 * @author Richard
 * @time 2019/7/16 18:18
 * @description 发送通知页面
 */
public class MyNotificationActivity extends AppCompatActivity {

    @BindView(R.id.btn_send_notification)
    Button btnSendNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_send_notification)
    public void onViewClicked() {
        Intent intent = new Intent(this,ShowNotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,intent,0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1",
                    "NotificationChannel", NotificationManager.IMPORTANCE_HIGH);
            Notification notification = new NotificationCompat.Builder(this,"1")
                    .setContentTitle("Notification")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("这个通知有以下的设置：" +
                            "标题、铃声、重要度、振动、绿色的呼吸灯、时间、小图标、意图行为、点击之后自动取消，" +
                            "还有现在能看见这么多文字的BigTextStyle"))
                    //.setSound(Uri.fromFile(new File("/emulated/0/tencent/qqfile_recv/63ac8513b87bb67e87042b856e2b6f01.mp3")))
                    //.setSound(getResources().openRawResource(R.raw.bell)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setVibrate(new long[] {0,1000,1000,1000})
                    .setLights(Color.GREEN,1000,1000)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            if (manager != null) {
                manager.createNotificationChannel(channel);
                manager.notify(1,notification);
            }
        }
    }
}
