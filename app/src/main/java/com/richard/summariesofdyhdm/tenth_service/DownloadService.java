package com.richard.summariesofdyhdm.tenth_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.richard.summariesofdyhdm.R;

import java.io.File;

/**
 * @author Richard
 * @time 2019/7/17 16:05
 * @param
 * @description 下载任务的服务，保证一直在后台运行
 */
public class DownloadService extends Service {

    private DownloadTask downloadTask;
    private String downloadUrl;

    private DownloadListener listener = new DownloadListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onProgress(int progress) {
            NotificationChannel channel = new NotificationChannel("1","channel",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getNotificationManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(channel);
                manager.notify(1,getNotification("Downloading...",progress,
                        "1"));
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onSuccess() {
            downloadTask = null;
            stopForeground(true);
            NotificationChannel channel = new NotificationChannel("2","channel",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getNotificationManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(channel);
                manager.notify(2,getNotification("Download Success",-1,
                        "2"));
                Toast.makeText(DownloadService.this,"Download Success",
                        Toast.LENGTH_SHORT).show();
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onFailed() {
            downloadTask = null;
            stopForeground(true);
            NotificationChannel channel = new NotificationChannel("3","channel",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getNotificationManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(channel);
                manager.notify(3,getNotification("Download Failed",-1,
                        "3"));
                Toast.makeText(DownloadService.this,"Download Failed",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this,"Paused",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void Cancled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"Canceld",Toast.LENGTH_SHORT).
                    show();
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{
        public void startDownload(String url) {
            if (downloadTask == null)  {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                startForeground(1,getNotification("Downloading...",0,
                        "1"));
                Toast.makeText(DownloadService.this,"Downloading...",
                        Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pausedDownload();
            }
        }

        public void canncelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            }
            if (downloadUrl != null) {
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.
                        DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);

                if (file.exists()) {
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_SHORT).
                        show();
            }
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
       return mBinder;
    }

    /**
     * @author Richard
     * @time 2019/7/17 16:26
     * @param
     * @description 获取NotificationManager实例
     */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * @author Richard
     * @time 2019/7/17 16:16
     * @param
     * @description 创建通知的服务
     */
    private Notification getNotification(String title,int progress,String channelId) {
        Intent intent = new Intent(this,MyActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                channelId);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if (progress >= 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();

    }

}
