package com.richard.summariesofdyhdm.tenth_service;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: Richard
 * @date: 2019/7/17
 * @describe:
 */
public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    //下载状态
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    /**
     * @author Richard
     * @time 2019/7/17 15:15
     * @param
     * @description 后台执行下载逻辑
     */
    @Override
    protected Integer doInBackground(String... strings) {

        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
            //记录已下载的文件长度
            long downloadedLength = 0;
            //拿到下载文件的URL地址
            String downloadUrl = strings[0];
            //得到文件的名字
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            //指定将文件下载到SD下的Download目录中
            String directory = Environment.getExternalStoragePublicDirectory(Environment.
                    DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            //如果文件存在，则已下载的长度就它的长度
            if (file.exists()) {
                downloadedLength = file.length();
            }
            //获取待下载文件的总长度
            long contentLength = getContentLength(downloadUrl);
            if (contentLength == 0) {
                return TYPE_FAILED;
            }else if (contentLength == downloadedLength) {
                return TYPE_SUCCESS;
            }
            //网络请求
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE","bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLength);
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    }else if (isPaused) {
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        savedFile.write(b,0,len);
                        //计算已经下载的百分比
                        int progress = (int) ((total + downloadedLength) * 100 /contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    /**
     * @author Richard
     * @time 2019/7/17 15:15
     * @param
     * @description 在界面上更新当前的下载进度
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            lastProgress = progress;
        }
    }

    /**
     * @author Richard
     * @time 2019/7/17 15:15
     * @param
     * @description 通知最终的下载结果
     */
    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.Cancled();
                break;
                default:
                    break;
        }
    }

    public void pausedDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    /**
     * @author Richard
     * @time 2019/7/17 15:15
     * @param
     * @description 获取带下载的文件长度
     */
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = 0;
            if (response.body() != null) {
                contentLength = response.body().contentLength();
            }
            response.body().close();
            return contentLength;
        }
        return 0;
    }

}
