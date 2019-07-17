package com.richard.summariesofdyhdm.ninth_network.parsejsondata;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.richard.summariesofdyhdm.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @param
 * @author Richard
 * @time 2019/7/17 9:47
 * @description OkHttp + Gson解析数据
 */
public class GsonActivity extends AppCompatActivity {

    @BindView(R.id.btn_gson)
    Button   btnGson;
    @BindView(R.id.tv_display_data_gson)
    TextView tvDisplayDataGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        ButterKnife.bind(this);
    }

    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @OnClick(R.id.btn_gson)
    public void onViewClicked() {
        sendRequest();
        Toast.makeText(this,"由于gson依赖错误，所以点击没有功能",Toast.LENGTH_SHORT).
                show();
    }
}
